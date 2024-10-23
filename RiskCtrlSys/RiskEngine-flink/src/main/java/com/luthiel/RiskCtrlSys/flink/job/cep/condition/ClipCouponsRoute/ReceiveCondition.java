package com.luthiel.RiskCtrlSys.flink.job.cep.condition.ClipCouponsRoute;

import com.luthiel.RiskCtrlSys.flink.utils.EventConstantUtil;
import com.luthiel.RiskCtrlSys.model.EventPO;
import org.apache.flink.cep.pattern.conditions.SimpleCondition;

/**
 * author: Luthiel
 * description: 过滤领取优惠券行为事件
 * date: 2023
 */

public class ReceiveCondition extends SimpleCondition<EventPO> {

    @Override
    public boolean filter(EventPO eventPO) throws Exception {
        return eventPO.getEvent_name().equals(EventConstantUtil.COUPON_USE);
    }
}
