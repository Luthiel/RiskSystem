package com.luthiel.RiskCtrlSys.flink.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.SerializeConfig;
import org.apache.doris.flink.cfg.DorisExecutionOptions;
import org.apache.doris.flink.cfg.DorisOptions;
import org.apache.doris.flink.cfg.DorisReadOptions;
import org.apache.doris.flink.sink.DorisSink;
import org.apache.doris.flink.sink.writer.SimpleStringSerializer;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;

import java.util.Properties;

public class DorisUtil {
    // Or we can write them in a `Constant File`
    public static final String DORIS_FE_NODES = "localhost:port";
    public static final String DORIS_DATABASE = "xxx";
    public static final String USER_NAME = "root";
    public static final String PASSWORD = "xxxxxx";


    public static void writeToDoris(SingleOutputStreamOperator<?> result, String tblName) {
        result.map(new DorisMapFuction<>())
                .sinkTo(getDorisSink(DORIS_DATABASE + "." + tblName, tblName));
    }

    private static DorisSink<String> getDorisSink(String table, String labelPrefix) {
        Properties prop = new Properties();
        prop.setProperty("format", "json");
        prop.setProperty("read_json_by_line", "true");

        return DorisSink.<String>builder()
                .setDorisReadOptions(DorisReadOptions.builder().build())
                .setDorisOptions(
                        DorisOptions.builder()
                                .setFenodes(DORIS_FE_NODES)
                                .setTableIdentifier(table)
                                .setUsername(USER_NAME)
                                .setPassword(PASSWORD)
                                .build()
                ).setDorisExecutionOptions(
                        DorisExecutionOptions.builder()
                                .setLabelPrefix(labelPrefix)
                                .disable2PC()
                                .setBufferCount(3)
                                .setBufferSize(1024 * 1024)
                                .setCheckInterval(3000)
                                .setMaxRetries(3)
                                .setStreamLoadProp(prop)
                                .build()
                ).setSerializer(new SimpleStringSerializer())
                .build();
    }

    private static class DorisMapFuction<T> implements MapFunction<T, String> {
        @Override
        public String map(T pojo) throws Exception {
            SerializeConfig conf = new SerializeConfig();
            //驼峰命名 转 下划线命名
            conf.setPropertyNamingStrategy(PropertyNamingStrategy.SnakeCase);
            return JSON.toJSONString(pojo, conf);
        }
    }
}
