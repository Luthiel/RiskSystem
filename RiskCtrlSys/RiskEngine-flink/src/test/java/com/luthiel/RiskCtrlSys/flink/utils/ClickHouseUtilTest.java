package com.luthiel.RiskCtrlSys.flink.utils;

import com.luthiel.RiskCtrlSys.model.CHTestPO;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * author: Luthiel
 * description: flink clickhouse读写工具类单元测试
 * date: 2023
 */

public class ClickHouseUtilTest {

    @DisplayName("测试Flink+jdbc+游标读取Clickhouse")
    @Test
    void testRead() throws Exception {
        //初始化环境
        StreamExecutionEnvironment env =
                StreamExecutionEnvironment.getExecutionEnvironment();

        // 设置并行度1
        env.setParallelism(1);

        // 从default数据库的ch_luthiel_test表中读取数据
        String sql = "select * from default.ch_luthiel_test";
        DataStream<CHTestPO> ds = ClickHouseUtil.read(env,sql);

        // 打印数据流中的元素
        ds.print("clickhouse");

        // 执行程序
        env.execute();

    }

    @DisplayName("测试Flink-Connector-jdbc+预编译批量写入Clickhouse")
    @Test
    void testBatchWrite() throws Exception {
        //初始化环境
        StreamExecutionEnvironment env =
                StreamExecutionEnvironment.getExecutionEnvironment();

        // 设置并行度1
        env.setParallelism(1);

        // 创建CHTestPO对象luthielTest1
        CHTestPO luthielTest1 = new CHTestPO();
        // 给luthielTest1的name属性赋值
        luthielTest1.setName("ch-luthiel-test-5");
        // 创建CHTestPO对象luthielTest2
        CHTestPO luthielTest2 = new CHTestPO();
        // 给luthielTest2的name属性赋值
        luthielTest2.setName("ch-luthiel-test-6");

        // 创建一个数据流ds，并将luthielTest1和luthielTest2添加到其中
        DataStream<CHTestPO> ds = env.fromCollection(Arrays.asList(luthielTest1, luthielTest2));

        // 定义将数据写入ClickHouse数据库的SQL语句
        String sql = "insert into default.ch_luthiel_test(name) values(?)";
        // 调用ClickHouseUtil的batchWrite方法将数据流ds中的数据批量写入ClickHouse数据库
        ClickHouseUtil.batchWrite(ds, sql, 2);

        // 执行程序
        env.execute();

    }
}
