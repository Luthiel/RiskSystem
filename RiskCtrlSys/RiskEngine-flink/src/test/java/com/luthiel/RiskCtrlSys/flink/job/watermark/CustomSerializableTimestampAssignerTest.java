package com.luthiel.RiskCtrlSys.flink.job.watermark;

import com.luthiel.RiskCtrlSys.flink.model.MysqlTestPO;
import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;

/**
 * author: Luthiel
 * description: TODO
 * date: 2023
 */

public class CustomSerializableTimestampAssignerTest implements SerializableTimestampAssigner<MysqlTestPO> {
    @Override
    public long extractTimestamp(MysqlTestPO mysqlTestPO, long l) {
        return mysqlTestPO.getTs_ms();
    }
}
