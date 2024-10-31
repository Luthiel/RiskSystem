package com.luthiel.RiskCtrlSys.model;

import lombok.Data;

/**
 * author: Luthiel
 * description: 单条规则 POJO 对象
 * date: 2023
 */

@Data
public class SingleRulePO {

    // 规则唯一标识
    private String rule_code;

    // 规则名称
    private String rule_name;

    // 适用规则的行为事件 (多个以,隔开)
    private String event_name;

    // 规则是否开启
    private String is_enable;

    // 条件表达式
    private String expression;

    // 策略动作
    private String action;

    // Mysql 操作时间戳 (用于生成水印)
    private long ts_ms;

    public SingleRulePO() {
    }

    public SingleRulePO(String rule_code, String rule_name, String event_name, String is_enable) {
        this.rule_code = rule_code;
        this.rule_name = rule_name;
        this.event_name = event_name;
        this.is_enable = is_enable;
    }
}
