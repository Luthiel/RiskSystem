package com.luthiel.RiskCtrlSys.flink;

import com.luthiel.RiskCtrlSys.commons.constants.ConstantsUtil;
import com.luthiel.RiskCtrlSys.flink.job.cep.pattern.DynamicPattern;
import com.luthiel.RiskCtrlSys.flink.job.watermark.MetricTimestampAssigner;
import com.luthiel.RiskCtrlSys.flink.utils.KafkaUtil;
import com.luthiel.RiskCtrlSys.flink.utils.ParameterUtil;
import com.luthiel.RiskCtrlSys.model.EventPO;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.cep.CEP;
import org.apache.flink.cep.PatternSelectFunction;
import org.apache.flink.cep.PatternStream;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.time.Duration;
import java.util.List;
import java.util.Map;

/**
 * author: Luthiel
 * description: 基于cep Pattern 的动态风控规则判断 Job ( 1个Job运行1条cep pattern )
 * date: 2023
 */


public class CepJudgeJob {
    public static void main(String[] args) throws Exception {
        /*
         * Flink配置 (获取配置文件以及任务提交参数)
         */
        ParameterTool tool = ParameterUtil.getParameters(args);
        // 获取任务提交参数传入的规则唯一编码
        String rule_code = tool.get(ConstantsUtil.ARGS_RULE_CODE);
        // 获取任务提交参数传入的 groovy 脚本名称
        String groovy_clazz = tool.get(ConstantsUtil.ARGS_GROOVY_NAME);

        // 消费 Kafka 数据，生成行为事件流，并按照用户 ID 分组
        DataStream<EventPO> eventStream = KafkaUtil.read(tool);

        DataStream<EventPO> eventStreamWithWatermarks = eventStream.assignTimestampsAndWatermarks(
                WatermarkStrategy
                        // Duration.ofSeconds(3)
                        .<EventPO>forBoundedOutOfOrderness(Duration.ZERO)
                        .withTimestampAssigner(new MetricTimestampAssigner()));

        KeyedStream<EventPO, Integer> keyedStream = eventStreamWithWatermarks.keyBy(EventPO::getUser_id_int);

        final StreamExecutionEnvironment env = KafkaUtil.env;

        /* **********************
         *
         * 实现Flink任务不停机的动态加载 cep 风控规则需要的前提：
         * 1. 动态的生成 Pattern (任务 **运行时**,如何触发 Groovy 重新解析脚本,生成 Pattern?)
         * 2. Flink 任务动态的加载新的 Pattern (任务运行时,如何触发 CEP 重新加载新的 Pattern?)
         *
         * 实现动态 cep 风控规则方案：
         * 1. 传递 Pattern 的动态配置 (定时器 or OperatorState, 推荐 OperatorState)
         * 2. 构建新的 Pattern
         * 3. 基于新的 Pattern 构建新的 NFA (非确定有限状态机)
         * 4. 队列数据的清理 (elementQueueState, ComputationState, PartialMatches)
         *
         * *********************/

        /* **********************
         *
         * 知识点：
         *
         * 1. 底层原理
         * cep 是通过 NFA 实现, Pattern 匹配的过程就是状态转换的过程
         *
         * 2. 源码程序实现
         * a. NFACompiler 将 Pattern 对象编译为状态, 即 Pattern 对象的每个模式都对应一个 cep State, 并放入一个叫 ComputationState 的队列
         * b. 每来一条数据，都会遍历这个 ComputationState 的队列, 看这条数据能否匹配队列里面的状态 (cep State),
         *    如果能匹配，就将数据放入缓存并匹配下一个状态
         * c. **所有状态** 都匹配成功的事件会放入 PartialMatches 的队列里
         *
         * 3. cep 源码：
         * 主要关注 CepOperator类 open(), 包含了 NFA构建  (重点)
         * 主要关注 CepOperator类 processElement(), 事件进入匹配
         * 主要关注 NFA 类 advanceTime() 超时事件处理
         * 主要关注 NFA 类 process() NFA 状态的更新
         *
         * *********************/

        /* **********************
         *
         * 修改 cep 源码, 返回携带自定义动态获取 Pattern 方法的 PatternStream
         * 自定义了 4 个方法, 2 个代码块
         *
         * *********************/

        PatternStream<EventPO> patternStream = CEP.luthielPatternStream(
                keyedStream,
                new DynamicPattern<EventPO>(groovy_clazz, rule_code))
                .inEventTimeLuthiel();

        /* **********************
         *
         * 修改cep源码, 实现Flink不停机的动态加载新的 Pattern
         * 在 CepOperator类 一共 11 个自定义代码块
         *
         * *********************/

        patternStream.select(new PatternSelectFunction<EventPO, String>() {
            @Override
            public String select(Map<String, List<EventPO>> pattern) throws Exception {
                //TODO 处理风险事件
                return "";
            }
        });


        env.execute();
    }
}
