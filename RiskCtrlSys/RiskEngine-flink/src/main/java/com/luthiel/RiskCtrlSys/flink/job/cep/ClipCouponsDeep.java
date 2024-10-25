package com.luthiel.RiskCtrlSys.flink.job.cep;

import com.luthiel.RiskCtrlSys.flink.job.cep.condition.ClipCouponsDeep.BrowseCondition;
import com.luthiel.RiskCtrlSys.flink.job.cep.condition.ClipCouponsDeep.EventIntervalCondition;
import com.luthiel.RiskCtrlSys.flink.job.cep.condition.ClipCouponsDeep.GradeCondition;
import com.luthiel.RiskCtrlSys.flink.utils.KafkaUtil;
import com.luthiel.RiskCtrlSys.model.EventPO;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.tuple.Tuple4;
import org.apache.flink.cep.CEP;
import org.apache.flink.cep.PatternStream;
import org.apache.flink.cep.functions.PatternProcessFunction;
import org.apache.flink.cep.pattern.Pattern;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.optimizer.operators.MapDescriptor;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;

import java.util.List;
import java.util.Map;

import static com.luthiel.RiskCtrlSys.utils.date.DateUtil.convertLocalDateTime2Timestamp;
import static com.luthiel.RiskCtrlSys.utils.date.DateUtil.convertStr2LocalDateTime;

/**
 * author: Luthiel
 * description: 账号等级为L2以上的用户各行为之间时间间隔平均少于3分钟或者浏览行为停留平均时间少于3分钟
 * date: 2023
 */

public class ClipCouponsDeep {

    public static void main(String[] args) {

        // Kafka
        DataStream<EventPO> eventStream = KafkaUtil.read(args);

        //生成KeyedStream
        KeyedStream<EventPO, Integer> keyedStream = eventStream
                .filter(obj -> "L2".equals(obj.getEvent_context().getProfile().getGrade()))
                .keyBy(EventPO::getUser_id_int);

        /*
        //生成模式 (规则/Pattern)
        Pattern<EventPO, ?> pattern =
                Pattern
                        .<EventPO>begin("before")
                        //账号等级为L2以上,才能进入模式
                        .where(new GradeCondition())
                        //前后行为事件,
                        .next("after")
                        //时间间隔平均少于3分钟
                        .where(new EventIntervalCondition())
                        //或者浏览行为停留平均时间少于3分钟
                        .or(new BrowseCondition());


        PatternStream<EventPO> patternStream = CEP.pattern(keyedStream, pattern);
        */

        SingleOutputStreamOperator<EventPO> processStream = keyedStream.process(new ProcessFunction<EventPO, EventPO>() {
            // event_name, duration, count
            private ValueState<Tuple2<String, Long>> lastEventState;
            private ValueState<Tuple2<Integer, Double>> commonAccState;
            private ValueState<Tuple2<Integer, Double>> browserAccState;

            @Override
            public void open(Configuration parameters) throws Exception {
                lastEventState = getRuntimeContext()
                        .getState(new ValueStateDescriptor<>("lastEvent", TypeInformation.of(new TypeHint<Tuple2<String, Long>>() {
                        })));
                commonAccState = getRuntimeContext()
                        .getState(new ValueStateDescriptor<>("commonAcc", TypeInformation.of(new TypeHint<Tuple2<Integer, Double>>() {
                        })));
                browserAccState = getRuntimeContext()
                        .getState(new ValueStateDescriptor<>("browserAcc", TypeInformation.of(new TypeHint<Tuple2<Integer, Double>>() {
                        })));
            }

            @Override
            public void processElement(EventPO eventPO, Context context, Collector<EventPO> collector) throws Exception {
                Tuple2<String, Long> lastEvent = lastEventState.value();
                String lastEventName = lastEvent.f0;
                String curEventName = eventPO.getEvent_name();
                Tuple2<Integer, Double> lastDiff = commonAccState.value();
                Tuple2<Integer, Double> lastBrowser = browserAccState.value();

                long lastTs = lastEvent.f1;
                long curTs = convertLocalDateTime2Timestamp(convertStr2LocalDateTime(eventPO.getEvent_time()));

                if (lastEventName == null) {
                    lastEventState.update(Tuple2.of(curEventName, curTs));
                } else {
                    if (!lastEventName.equals(curEventName) && !"browser".equals(lastEventName)) {

                        int cnt = lastDiff.f0 + 1;
                        double ts = lastDiff.f0 * lastDiff.f1 + curTs - lastTs;
                        commonAccState.update(Tuple2.of(cnt, ts / cnt));

                    } else if (!lastEventName.equals(curEventName) && "browser".equals(lastEventName)) {

                        int cnt = lastBrowser.f0 + 1;
                        double ts = lastBrowser.f0 * lastBrowser.f1 + curTs - lastTs;
                        browserAccState.update(Tuple2.of(cnt, ts / cnt));

                    } else if (lastEventName.equals(curEventName) && !"browser".equals(curEventName)) {

                        int cnt = lastDiff.f0;
                        double ts = lastDiff.f0 * lastDiff.f1 + curTs - lastTs;
                        commonAccState.update(Tuple2.of(cnt, ts / cnt));

                    } else if (lastEventName.equals(curEventName) && "browser".equals(curEventName)) {

                        int cnt = lastBrowser.f0;
                        double ts = lastBrowser.f0 * lastBrowser.f1 + curTs - lastTs;
                        browserAccState.update(Tuple2.of(cnt, ts / cnt));

                    }
                }
                int threshold = 3 * 60 * 1000;
                if (commonAccState.value().f1 < threshold || browserAccState.value().f1 < threshold) {
                    collector.collect(eventPO);
                }
            }
        });

//
//        patternStream.process(new PatternProcessFunction<EventPO, EventPO>() {

//

//
//            @Override
//            public void processMatch(Map<String, List<EventPO>> match, Context ctx, Collector<EventPO> out) throws Exception {
//                Tuple2<Integer, Integer> commonAcc = commonAccState.value();
//                Tuple2<Integer, Integer> browserAcc = browserAccState.value();
//
//
//            }
//        })

    }
}
