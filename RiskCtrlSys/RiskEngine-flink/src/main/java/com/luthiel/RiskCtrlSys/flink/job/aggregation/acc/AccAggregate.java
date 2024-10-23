package com.luthiel.RiskCtrlSys.flink.job.aggregation.acc;

/**
 * author: Luthiel
 * description: 累加器计算方法接口
 * date: 2023
 */

public interface AccAggregate {

    public Double aggregate(String value_before, String value_after);
}
