package com.luthiel.RiskCtrlSys.flink.redis.source;

import com.luthiel.RiskCtrlSys.flink.redis.conf.LuthielRedisCommand;
import com.luthiel.RiskCtrlSys.flink.redis.conf.JedisBuilder;
import com.luthiel.RiskCtrlSys.flink.redis.conf.JedisConf;
import com.luthiel.RiskCtrlSys.model.RedisPO;
import org.apache.flink.api.common.state.ListState;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;
import redis.clients.jedis.JedisCluster;

/**
 * author: Luthiel
 * description: Flink 自定义Redis Source读取Redis
 * date: 2023
 */

/* **********************
 *
 * 知识点：
 *
 * 一：
 *
 * RichSourceFunction RichSinkFunction,RichMapFunction 富函数类
 * Flink API集合分为2大类：
 * 1. 函数类 (MapFunction)
 * 2. 富函数类 (RichMapFunction)
 *
 * 富函数类 比函数类提供了更多函数生命周期，提供了获取上下文的方法
 * 富函数类通常是抽象类
 *
 * 二：
 *
 * RichParallelSourceFunction 和 RichSourceFunction 不同：
 * RichParallelSourceFunction 可以设置并行度
 * RichParallelSourceFunction  和 RichSourceFunction 代码是可以互相套用
 *
 * RichParallelSourceFunction 默认的并行度是cpu 的 core数
 * RichSourceFunction 的并行度只能是1
 *
 * *********************/
public class LuthielRedisSource extends RichSourceFunction<RedisPO> {

    /**
     * Jedis对象
     */
    private JedisBuilder jedisBuilder;

    /**
     * Redis命令枚举对象
     */
    private final LuthielRedisCommand luthielRedisCommand;

    /**
     * redis key
     */
    private final String key;


    /**
     * pattern 状态 (OperatorState类型)
     */
    private transient ListState<RedisPO> patternState;

    public LuthielRedisSource(LuthielRedisCommand luthielRedisCommand, String key) {
        this.luthielRedisCommand = luthielRedisCommand;
        this.key = key;
    }

    /* **********************
     *
     * 知识点：
     *
     * volatile 修饰的变量，它的更新都会通知其他线程.
     *
     * *********************/
    private volatile boolean isRunning = true;

    /**
     * author: Luthiel
     * description: Redis数据的读取
     * @param output:
     * @return void
     */
    @Override
    public void run(SourceContext<RedisPO> output) throws Exception {

        /* **********************
         *
         * 一直监听Redis数据的读取
         *
         * *********************/

        String data = null;
        //while (isRunning) {

            switch (luthielRedisCommand.getLuthielRedisDataType()) {
                case STRING :
                    data = jedisBuilder.get(key);
            }
            RedisPO redisPO = new RedisPO(data);

            output.collect(redisPO);
        //}

    }


    @Override
    public void cancel() {
        this.isRunning = false;
    }

    /**
     * author: Luthiel
     * description: Redis的连接初始化
     * @param parameters:
     * @return void
     */
    @Override
    public void open(Configuration parameters) throws Exception {
        JedisCluster jedisCluster = JedisConf.getJedisCluster();
        jedisBuilder = new JedisBuilder(jedisCluster);

    }



}