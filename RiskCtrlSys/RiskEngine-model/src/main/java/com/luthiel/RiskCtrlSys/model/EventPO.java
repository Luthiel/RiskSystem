package com.luthiel.RiskCtrlSys.model;

import lombok.Data;

/**
 * author: Luthiel
 * description: 用户事件行为的POJO对象
 * date: 2023
 */

@Data
public class EventPO {
     // 注意：属性名称要和写入到Mysql的属性名称一致
     // 用户 ID
    private Integer user_id_int;

     // 事件发生时间
    private String event_time;

     // 事件目标名称
    private String event_target_name;

    // 事件名称
    private String event_name;

    // 事件类型
    private String event_type;

    // 事件上下文
    private EventContextPO event_context;

    // 指标各配置参数
    private MetricsConfPO metrics_conf;

    // 原子规则
    private SingleRulePO singleRule;

    // 规则组
    private RulesPO rules;

    // Kafka 偏移量、分区
    private long offset;
    private int partition;

    public EventPO() {
    }

    public EventPO(
            Integer user_id_int,
            String event_time,
            String event_target_name,
            String event_name,
            String event_type,
            EventContextPO event_context) {
        this.user_id_int = user_id_int;
        this.event_time = event_time;
        this.event_target_name = event_target_name;
        this.event_name = event_name;
        this.event_type = event_type;
        this.event_context = event_context;
    }

}
