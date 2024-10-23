package com.luthiel.RiskCtrlSys.flink.utils;

import com.luthiel.RiskCtrlSys.flink.redis.conf.LuthielRedisCommand;
import com.luthiel.RiskCtrlSys.flink.redis.source.LuthielRedisSource;
import com.luthiel.RiskCtrlSys.model.RedisPO;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * author: Luthiel
 * description: Flink读取Redis工具类
 * date: 2023
 */

public class RedisReadUtil {

    /**
     * author: Luthiel
     * description: Flink添加Source
     * @param env: Flink上下文环境
     * @param luthielRedisCommand: redis命令
     * @param key:  redis键
     * @return org.apache.flink.streaming.api.datastream.DataStream<com.luthiel.RiskCtrlSys.model.RedisPO>
     */
    public static DataStream<RedisPO> read(
            StreamExecutionEnvironment env,
            LuthielRedisCommand luthielRedisCommand,
            String key
            ) {

       return env.addSource(new LuthielRedisSource(luthielRedisCommand,key));
    }
}
