package com.vaio.io.spark.batch

import org.apache.spark.sql.SparkSession

/**
 *
 * @author yao.wang (Yao.MR.CN@GMail.com)
 * @date 2021-05-27
 */
object SparkTest {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .master("local")
      .appName("JavaWordCount")
      .getOrCreate();


  }
}
