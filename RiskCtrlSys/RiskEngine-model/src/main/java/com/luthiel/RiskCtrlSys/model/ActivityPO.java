package com.luthiel.RiskCtrlSys.model;

import lombok.Data;

import java.util.List;

/**
 * author: Luthiel
 * description: 策略 POJO
 * date: 2023
 */

@Data
public class ActivityPO {

    // 策略名称
    private String activation_name;

    // 策略动作：一种策略可以实施多种动作，比如打标、禁用...
    private List<ActionPO> actions;
}
