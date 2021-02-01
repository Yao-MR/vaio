package com.vaio.flink.stream.jobs;

import com.vaio.io.flink.core.executors.DailyExecutor;
import com.vaio.io.flink.core.jobs.FlinkJob;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

/**
 * @author yao.wang, (yao.wang@leyantech.com)
 * @test: nc -lk 9000在mac的端口启动，并启动程序，对控制台的输入进行监控，并进行相关逻辑的计算。
 * @doc: 对nc端口上以回车换行分割的字符串进行相关的数字统计。
 * @date 2019-05-23.
 */
public class SocketWindowWordCount extends FlinkJob implements DailyExecutor {

    @Override
    public void executeOn() throws Exception {

        StreamExecutionEnvironment sEnv = sEnv();
        DataStream<String> text = sEnv.socketTextStream("localhost", 9000, "\n");
        //将流数据源按照回车进行分割形成字符集合
        text
                .flatMap(new FlatMapFunction<String, Tuple2<String, Integer>>() {
                    @Override
                    public void flatMap(String value, Collector<Tuple2<String, Integer>> out) {
                        for (String word : value.split("\\s")) {
                            out.collect(Tuple2.of(word, 1));
                        }
                    }
                })
                .map(x -> x.f0)
                .print()
                .setParallelism(1);
        System.out.println(sEnv.getExecutionPlan());
        sEnv.execute("Socket Window WordCount");
    }


    public static void main(String args[]) {

        new SocketWindowWordCount().execute();
    }

}
