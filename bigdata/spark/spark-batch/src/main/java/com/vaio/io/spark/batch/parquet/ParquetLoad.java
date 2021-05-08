package com.vaio.io.spark.batch.parquet;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.SparkSession;
import scala.Tuple2;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 * @author yao.wang, (vaio.MR.CN@GMail.com)
 * @date 2020-05-11
 */
public class ParquetLoad {

    private static final Pattern SPACE = Pattern.compile(" ");

    public static void main(String[] args) throws Exception {


        SparkSession spark = SparkSession
                .builder()
                .master("local")
                .appName("ParquetLoad")
                .getOrCreate();

        JavaRDD<String> lines = spark.sparkContext().textFile("/Users/ywang46/WorkSpace/intell/vaio/bigdata/spark/spark-batch/src/main/resources/name.txt",2).toJavaRDD();
        JavaRDD<String> words = lines.flatMap(s -> Arrays.asList(SPACE.split(s)).iterator());

        JavaPairRDD<String, Integer> ones = words.mapToPair(s -> new Tuple2<>(s, 1));

        JavaPairRDD<String, Integer> counts = ones.reduceByKey((i1, i2) -> i1 + i2);

        List<Tuple2<String, Integer>> output = counts.collect();
        for (Tuple2<?,?> tuple : output) {
            System.out.println(tuple._1() + ": " + tuple._2());
        }
        spark.stop();
    }

}