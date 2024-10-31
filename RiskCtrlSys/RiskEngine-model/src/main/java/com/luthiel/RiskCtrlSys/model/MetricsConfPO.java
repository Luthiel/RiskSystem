package com.luthiel.RiskCtrlSys.model;

import lombok.Data;

/**
 * author: Luthiel
 * description: 指标配置参数POJO对象
 * date: 2023
 */


@Data
public class MetricsConfPO {
    /*
     * tips:
     * 1. 属性名称必须和 Mysql 表 metric_attr 的字段名称一致
     * 2. 为了后面反射赋值的方便, 属性数据类型都是 String
     */

    // 指标名称
    private String metric_name;
    private Long metric_id;
    private Long rule_id;
    // 指标编码
    private String metric_code;
    // 指标存储路径
    private String metric_store;
    // 指标主维度
    private String main_dim;
    // flink 窗口大小
    private String window_size;
    // flink 窗口步长
    private String window_step;
    // flink 窗口类型
    private String window_type;
    // flink 过滤器名称
    private String flink_filter;
    // flink 键控对象
    private String flink_keyby;
    // flink 水印策略
    private String flink_watermark;
    // 聚合计算方式
    private String metric_agg_type;
    // 聚合函数
    private String aggregation;
    // 是否开启某项配置
    private String is_enable;
    // 场景
    private String scene;
    private String event;
}
