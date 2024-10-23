package com.luthiel.RiskCtrlSys.flink.utils;

import org.apache.flink.api.java.utils.ParameterTool;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * author: Luthiel
 * description: ParameterTool读取配置单元测试
 * date: 2023
 */

public class ParameterUtilTest {

    @DisplayName("测试不同环境的ParameterTool读取到的不同配置信息")
    @Test
    void testGetParametersWithEnv() {
        ParameterTool tool = ParameterUtil.getParameters();

        // dev环境的kafka topic名称
        System.out.println(tool.get(ParameterConstantsUtil.KAFKA_TOPIC));
        assertEquals(
                "luthieltest",
                tool.get(ParameterConstantsUtil.KAFKA_TOPIC));
    }

}