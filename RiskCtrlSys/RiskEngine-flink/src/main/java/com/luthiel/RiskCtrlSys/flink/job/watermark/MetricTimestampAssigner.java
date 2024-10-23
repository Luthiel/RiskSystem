package com.luthiel.RiskCtrlSys.flink.job.watermark;

import com.luthiel.RiskCtrlSys.model.EventPO;
import com.luthiel.RiskCtrlSys.utils.date.DateUtil;
import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;

import java.time.LocalDateTime;

/**
 * author: Luthiel
 * description: 自定义水印时间戳生成器
 * date: 2023
 */

public class MetricTimestampAssigner implements SerializableTimestampAssigner<EventPO> {

    /**
     * author: Luthiel
     * description: 提取事件流的event_time字段
     * @param eventPO:
     * @param l:
     * @return long
     */
    @Override
    public long extractTimestamp(EventPO eventPO, long l) {

        //提取时间字段, 并转换时间戳，时间戳是毫秒。
        LocalDateTime localDateTime = DateUtil.convertStr2LocalDateTime(eventPO.getEvent_time());
        return DateUtil.convertLocalDateTime2Timestamp(localDateTime);
    }
}