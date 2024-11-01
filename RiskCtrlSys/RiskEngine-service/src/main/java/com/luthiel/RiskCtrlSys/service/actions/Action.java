package com.luthiel.RiskCtrlSys.service.actions;


import com.luthiel.RiskCtrlSys.model.EventPO;
import com.luthiel.RiskCtrlSys.model.RulesPO;

public interface Action {
    public void takeAction(RulesPO rules, EventPO event);
}
