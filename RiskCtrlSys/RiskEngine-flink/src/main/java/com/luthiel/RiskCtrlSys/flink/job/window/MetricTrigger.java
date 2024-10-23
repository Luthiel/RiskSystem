package com.luthiel.RiskCtrlSys.flink.job.window;

import com.luthiel.RiskCtrlSys.model.EventPO;
import org.apache.flink.api.common.state.ReducingStateDescriptor;
import org.apache.flink.api.common.state.State;
import org.apache.flink.api.common.state.StateDescriptor;
import org.apache.flink.streaming.api.windowing.triggers.Trigger;
import org.apache.flink.streaming.api.windowing.triggers.TriggerResult;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;

/**
 * author: Luthiel
 * description: 指标 窗口触发器 模块
 * date: 2023
 */

public class MetricTrigger extends Trigger<EventPO, TimeWindow> {

    /* **********************
     * ReducingStateDescriptor 要和 ReduceFunction 一起使用
     *
     * *********************/

    //聚合状态
    //private StateDescriptor state = new ReducingStateDescriptor("",,Integer.class);


    /**
     * author: Luthiel
     * description: 基于窗口内数据量的触发器
     * @param eventPO:
     * @param l:
     * @param timeWindow:
     * @param triggerContext:
     * @return org.apache.flink.streaming.api.windowing.triggers.TriggerResult
     */
    @Override
    public TriggerResult onElement(EventPO eventPO, long l, TimeWindow timeWindow, TriggerContext triggerContext) throws Exception {

        //获取状态
//        State count = triggerContext.getPartitionedState(state);
//        count.add(1);
//        triggerContext.registerEventTimeTimer(timeWindow.maxTimestamp());
//        if (count.get()) {
//            count.clear();
//            return TriggerResult.FIRE_AND_PURGE;
//        }else{
//            return TriggerResult.CONTINUE;
//        }
        return null;
    }

    /**
     * author: Luthiel
     * description: 基于ProcessingTime时间语义的触发器
     * @param l:
     * @param timeWindow:
     * @param triggerContext:
     * @return org.apache.flink.streaming.api.windowing.triggers.TriggerResult
     */
    @Override
    public TriggerResult onProcessingTime(long l, TimeWindow timeWindow, TriggerContext triggerContext) throws Exception {
        //不做任何处理
        return TriggerResult.CONTINUE;
    }

    /**
     * author: Luthiel
     * description: 基于EventTime时间语义的触发器
     * @param l:
     * @param timeWindow:
     * @param triggerContext:
     * @return org.apache.flink.streaming.api.windowing.triggers.TriggerResult
     */
    @Override
    public TriggerResult onEventTime(long l, TimeWindow timeWindow, TriggerContext triggerContext) throws Exception {
        //不做任何处理
        return TriggerResult.CONTINUE;
    }

    /**
     * author: Luthiel
     * description: 清除操作
     * @param timeWindow:
     * @param triggerContext:
     * @return void
     */
    @Override
    public void clear(TimeWindow timeWindow, TriggerContext triggerContext) throws Exception {

    }
}
