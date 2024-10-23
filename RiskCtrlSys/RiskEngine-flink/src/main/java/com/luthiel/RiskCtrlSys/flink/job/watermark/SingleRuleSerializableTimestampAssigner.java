package com.luthiel.RiskCtrlSys.flink.job.watermark;

import com.luthiel.RiskCtrlSys.model.SingleRulePO;
import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;

/**
 * author: Luthiel
 * description: 原子规则流自定义生成水印
 * date: 2023
 */

public class SingleRuleSerializableTimestampAssigner implements SerializableTimestampAssigner<SingleRulePO> {
    @Override
    public long extractTimestamp(SingleRulePO singleRulePO, long l) {
        return singleRulePO.getTs_ms();
    }
}
