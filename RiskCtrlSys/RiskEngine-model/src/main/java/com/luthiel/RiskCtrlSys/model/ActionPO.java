package com.luthiel.RiskCtrlSys.model;

import lombok.Data;

/**
 * author: Luthiel
 * description: 策略动作 POJO
 * date: 2023
 */

@Data
public class ActionPO {

    // 策略动作
    private String action;
    // 风险信息
    private RiskInfoPO info;
}
