package com.luthiel.RiskCtrlSys.flink.job.cep.condition.ClipCouponsDeep;

import com.luthiel.RiskCtrlSys.model.EventPO;
import org.apache.flink.cep.pattern.conditions.IterativeCondition;

import static com.luthiel.RiskCtrlSys.utils.date.DateUtil.convertLocalDateTime2Timestamp;
import static com.luthiel.RiskCtrlSys.utils.date.DateUtil.convertStr2LocalDateTime;

/**
 * author: Luthiel
 * description: TODO 浏览行为停留平均时间少于3分钟
 * date: 2023
 */

public class BrowseCondition extends IterativeCondition<EventPO> {

    @Override
    public boolean filter(EventPO eventPO, Context<EventPO> ctx) throws Exception {
        EventPO preEventPO = ctx.getEventsForPattern("before").iterator().next();

        if ("browse".equals(preEventPO.getEvent_name()) && "browse".equals(eventPO.getEvent_name())) {
            long preTs = convertLocalDateTime2Timestamp(convertStr2LocalDateTime(preEventPO.getEvent_time()));
            long curTs = convertLocalDateTime2Timestamp(convertStr2LocalDateTime(eventPO.getEvent_time()));
            return curTs - preTs < 3 * 60 * 1000;
        }
        return false;
    }
}