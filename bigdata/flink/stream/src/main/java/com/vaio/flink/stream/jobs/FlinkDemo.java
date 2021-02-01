package com.vaio.flink.stream.jobs;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.runtime.state.filesystem.FsStateBackend;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class FlinkDemo {
    public static void main(String args[]) throws Exception {

        StreamExecutionEnvironment sEnv = StreamExecutionEnvironment.getExecutionEnvironment();
        sEnv.enableCheckpointing(1000);
        sEnv.getCheckpointConfig().setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE);
        // 确保检查点之间有至少500 ms的间隔【checkpoint最小间隔】
        sEnv.getCheckpointConfig().setMinPauseBetweenCheckpoints(500);
        // 检查点必须在一分钟内完成，或者被丢弃【checkpoint的超时时间】
        sEnv.getCheckpointConfig().setCheckpointTimeout(60000);
        // 同一时间只允许进行一个检查点
        sEnv.getCheckpointConfig().setMaxConcurrentCheckpoints(1);
        // 表示一旦Flink处理程序被cancel后，会保留Checkpoint数据，以便根据实际需要恢复到指定的   Checkpoint【详细解释见备注】
        sEnv.getCheckpointConfig().enableExternalizedCheckpoints(CheckpointConfig.ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION);
        sEnv.setStateBackend(new FsStateBackend("hdfs://namenode:9000/flink/checkpoints"));
        sEnv.socketTextStream("localhost", 9000, "\n")
                .flatMap((FlatMapFunction<String, Tuple2<String, Integer>>) (value, out) -> {
                    for (String word : value.split("\\s")) {
                        out.collect(Tuple2.of(word, 1));
                    }
                })
                .map(x -> x.f0)
                .print()
                .setParallelism(1);
        System.out.println(sEnv.getExecutionPlan());
        sEnv.execute("Socket Window WordCount");
    }
}
