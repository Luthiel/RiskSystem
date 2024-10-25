package com.luthiel.RiskCtrlSys.flink.job.cep.condition.ClipCouponsDeep;

import com.luthiel.RiskCtrlSys.model.EventPO;
import com.luthiel.RiskCtrlSys.utils.date.DateUtil;
import org.apache.flink.cep.pattern.conditions.IterativeCondition;
import org.apache.flink.cep.pattern.conditions.SimpleCondition;


import static com.luthiel.RiskCtrlSys.utils.date.DateUtil.convertLocalDateTime2Timestamp;
import static com.luthiel.RiskCtrlSys.utils.date.DateUtil.convertStr2LocalDateTime;

/**
 * author: Luthiel
 * description: TODO 时间间隔平均少于3分钟 SimpleCondition重写的filter方法只有事件一个参数，没有上下文参数
 * date: 2023
 */
public class EventIntervalCondition extends IterativeCondition<EventPO> {

    @Override
    public boolean filter(EventPO eventPO, Context<EventPO> ctx) throws Exception {
        EventPO preEventPO = ctx.getEventsForPattern("before").iterator().next();

        // 不同行为之间时间间隔
        if (!preEventPO.getEvent_name().equals(eventPO.getEvent_name())) {
            long preTs = convertLocalDateTime2Timestamp(convertStr2LocalDateTime(preEventPO.getEvent_time()));
            long curTs = convertLocalDateTime2Timestamp(convertStr2LocalDateTime(eventPO.getEvent_time()));
            return curTs - preTs < 3 * 60 * 1000;
        }

        return false;
    }
}
