package com.luthiel.RiskCtrlSys.flink.job.cep.select;

import com.luthiel.RiskCtrlSys.model.EventPO;
import org.apache.flink.cep.functions.PatternProcessFunction;
import org.apache.flink.util.Collector;

import java.util.List;
import java.util.Map;

/**
 * author: Luthiel
 * description: 最近15分钟内IP更换次数超过3次的用户的提取
 * date: 2023
 */

public class IpChangeProcessFunction extends PatternProcessFunction<EventPO, EventPO> {
    @Override
    public void processMatch(Map<String, List<EventPO>> map, Context context, Collector<EventPO> collector) throws Exception {

    }
}
