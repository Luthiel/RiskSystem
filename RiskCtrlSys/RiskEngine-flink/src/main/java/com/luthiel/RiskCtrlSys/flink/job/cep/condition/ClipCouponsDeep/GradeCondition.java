package com.luthiel.RiskCtrlSys.flink.job.cep.condition.ClipCouponsDeep;

import com.luthiel.RiskCtrlSys.model.EventPO;
import org.apache.flink.cep.pattern.conditions.SimpleCondition;

/**
 * author: Luthiel
 * description: 账号等级条件，L1 以上
 * date: 2023
 */

public class GradeCondition extends SimpleCondition<EventPO> {

    @Override
    public boolean filter(EventPO eventPO) throws Exception {

        if(!eventPO.getEvent_context().getProfile().getGrade().equals("L1")) {
            return true;
        }
        return false;
    }
}
