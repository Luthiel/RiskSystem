package com.luthiel.RiskCtrlSys.flink.utils;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RedisWriteUtilTest {

    @DisplayName("测试基于Bahir写入Redis,Redis数据类型是String类型")
    @Test
    void testWriteByBahirWithString() throws Exception {
        StreamExecutionEnvironment env =
                StreamExecutionEnvironment.getExecutionEnvironment();

        Tuple2<String,String> tuple = Tuple2.of("luthiel:bahir", "this is write by bahir with String");
        DataStream<Tuple2<String,String>> dataStream = env.fromElements(tuple);

        RedisWriteUtil.writeByBahirWithString(dataStream);

        env.execute();
    }
}
