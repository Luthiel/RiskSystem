package com.luthiel.RiskCtrlSys.flink.utils;

import com.luthiel.RiskCtrlSys.model.EventPO;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * author: Luthiel
 * description: Flink Kafka读写工具类单元测试类
 * date: 2023
 */

public class KafkaUtilTest {

    @DisplayName("测试Flink-Connector-kafka读取Kafka数据并清洗为EventPO类型的事件流")
    @Test
    public void testFlinkReadKafka() throws Exception {

        ParameterTool parameterTool = ParameterUtil.getParameters();

        DataStream<EventPO> eventStream = KafkaUtil.read(parameterTool);
        StreamExecutionEnvironment env = KafkaUtil.env;
        env.setParallelism(3);

        eventStream.print();
        env.execute();
    }

    @DisplayName("测试打印出类型为事件行为POJO对象的事件流")
    @Test
    void testEventPOStream() throws Exception {
        ParameterTool parameterTool = ParameterUtil.getParameters();
        StreamExecutionEnvironment env = KafkaUtil.env;
        env.setParallelism(3);

        DataStream<EventPO> eventStream = KafkaUtil.read(parameterTool);

        // 打印
        eventStream.print();

        env.execute();
    }

}
