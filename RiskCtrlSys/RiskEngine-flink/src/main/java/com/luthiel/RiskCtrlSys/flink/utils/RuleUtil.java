package com.luthiel.RiskCtrlSys.flink.utils;

import com.luthiel.RiskCtrlSys.flink.job.broadcast.CepKeyedBroadcastProcessFunc;
import com.luthiel.RiskCtrlSys.flink.job.broadcast.RulesKeyedBroadcastProcessFunc;
import com.luthiel.RiskCtrlSys.model.EventPO;
import com.luthiel.RiskCtrlSys.model.RedisPO;
import com.luthiel.RiskCtrlSys.model.RulesPO;
import com.luthiel.RiskCtrlSys.model.SingleRulePO;
import org.apache.flink.api.common.state.MapStateDescriptor;
import org.apache.flink.api.common.typeinfo.BasicTypeInfo;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.*;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * author: Luthiel
 * description: 风控规则工具类
 * date: 2023
 */

public class RuleUtil {

    public static <T> MapStateDescriptor<String, T> getMapState(String name,Class<T> clazz) {
            return new MapStateDescriptor<String, T>(
                    name,
                    BasicTypeInfo.STRING_TYPE_INFO,
                    TypeInformation.of(clazz)
            );
        }



    /**
     * author: Luthiel
     * description: 名单遍历, 判断是否在名单中
     * @param rosterType:  名单类型
     * @param uid:
     * @return boolean
     */
    public static boolean traversalRoster(
            String rosterType,
            Integer uid) {

        /* **********************
         *
         * 名单判断在 Redis 的 集合(set) 中进行
         *
         * *********************/

        return false;
    }


    /**
     * author: Luthiel
     * description: 规则组内的规则遍历
     * @param event_name: 行为事件名称
     * @return boolean
     */
    public static boolean traversalRules(
            String event_name,
            RulesPO rules) {

        //规则组内规则是否全部命中
        boolean judgeTag = true;
        //遍历规则组
        for(SingleRulePO rule:rules.getRules()) {

            //根据 event_name 使用对应的规则判断
            String[] eventNames = rule.getEvent_name().split(",");
            for(String eventName:eventNames) {
                if (eventName.equals(event_name)) {
                    if (!ruleJudgment(rule)) {
                        judgeTag = false;
                    }
                }
            }
        }

        //规则组内规则全部命中执行策略
        if(judgeTag) {

        }

        return false;
    }

    /**
     * author: Luthiel
     * description: 规则判断
     * @param rule:
     * @return boolean
     */
    public static boolean ruleJudgment(SingleRulePO rule) {

        return true;
    }

    /**
     * author: Luthiel
     * description: 生成规则组广播流
     * @param env:
     * @param parameterTool:
     * @param set_code:
     * @return org.apache.flink.streaming.api.datastream.BroadcastStream<com.luthiel.RiskCtrlSys.model.RulesPO>
     */
    public static SingleOutputStreamOperator<EventPO> doRuleBroadcastStream(
            StreamExecutionEnvironment env,
            ParameterTool parameterTool,
            DataStream<RulesPO> rulesStream,
            KeyedStream<EventPO, Integer> keyedStream,
            String set_code
    ) throws Exception{

        //建立MapState解析器
        //key 是规则组的唯一编码
        MapStateDescriptor<String, RulesPO>mapState = getMapState("rulesBroadcastState",RulesPO.class);


        //转化为广播流
        BroadcastStream<RulesPO> broadcastStream = DataStreamUtil.broadcastStreamBuilder(
                        rulesStream,
                        mapState
                );

        //流合并
        BroadcastConnectedStream<EventPO, RulesPO> connectStream  =DataStreamUtil.streamConnect(keyedStream,broadcastStream);

        //将规则组写入行为事件
        return DataStreamUtil.processFuncWithKey(
                                connectStream,
                                new RulesKeyedBroadcastProcessFunc(mapState,set_code)
                                );
    }


    /**
     * author: Luthiel
     * description: 生成cep pattern广播流
     * @param env:
     * @param parameterTool:
     * @param rulesStream:
     * @param keyedStream:
     * @param set_code:
     * @return org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator<com.luthiel.RiskCtrlSys.model.EventPO>
     */
    public static SingleOutputStreamOperator<EventPO> doPatternBroadcastStream(
            StreamExecutionEnvironment env,
            ParameterTool parameterTool,
            DataStream<RedisPO> rulesStream,
            KeyedStream<EventPO, Integer> keyedStream,
            String set_code
    ) throws Exception{

        //建立MapState解析器
        //key 是规则组的唯一编码
        MapStateDescriptor<String, RedisPO>mapState = getMapState("cepBroadcastState",RedisPO.class);


        //转化为广播流
        BroadcastStream<RedisPO> broadcastStream = DataStreamUtil.broadcastStreamBuilder(
                rulesStream,
                mapState
        );

        //流合并
        BroadcastConnectedStream<EventPO, RedisPO> connectStream  =DataStreamUtil.streamConnect(keyedStream,broadcastStream);

        //将规则组写入行为事件
        return DataStreamUtil.processFuncWithKey(
                connectStream,
                new CepKeyedBroadcastProcessFunc(mapState,set_code)
        );
    }

}
