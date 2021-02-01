package com.vaio.flink.stream.jobs;

import com.vaio.io.flink.core.executors.DailyExecutor;
import com.vaio.io.flink.core.jobs.FlinkJob;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;
import org.apache.flink.util.OutputTag;

/**
 * 背景:
 *      对用户的输入单词流进行处理, 字符含有5的字符标示为异常字符，进行旁路输出
 * 思路:
 *      使用flink的旁路输出的功能
 * 算法:
 *      对流的字符是否包含进行5字符进行判定
 * 参考:
 *      flink的旁路输出
 *
 * @author yao.wang
 * @date 2020-12-02
 */
public class SideOut extends FlinkJob implements DailyExecutor {
    @Override
    public void executeOn() throws Exception {
        final OutputTag<String> outputTag = new OutputTag<String>("side-output") {
        };

        StreamExecutionEnvironment sEnv = sEnv();
        SingleOutputStreamOperator<String> text = sEnv
                .socketTextStream("localhost", 9000, "\n")
                .process(new ProcessFunction<String, String>() {
                    @Override
                    public void processElement(String value, Context ctx, Collector<String> out) throws Exception {
                        if (value.contains("5")) {
                            ctx.output(outputTag, "异常数据旁路输出：" + value);
                        } else {
                            out.collect("正常数据正常输出：" + value);
                        }
                    }
                });

        DataStream<String> normalStream = text;
        DataStream<String> sideOutStream = text.getSideOutput(outputTag);

        normalStream.print();
        sideOutStream.print();

        sEnv.execute("Socket Window WordCount");
    }

    public static void main(String args[]) {
        new SideOut().execute();
    }
}