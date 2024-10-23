package com.luthiel.RiskCtrlSys.flink.job.watermark;

import com.luthiel.RiskCtrlSys.model.RulesPO;
import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;

/**
 * author: Luthiel
 * description: 规则组流自定义生成水印
 * date: 2023
 */

public class RulesSerializableTimestampAssigner implements SerializableTimestampAssigner<RulesPO> {
    @Override
    public long extractTimestamp(RulesPO rulesPO, long l) {
        return rulesPO.getTs_ms();
    }
}
