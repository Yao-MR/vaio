package com.vaio.io.spark.core

import org.apache.spark.sql.types.{StructField, StructType}
import org.apache.spark.sql.{DataFrame, Row, SaveMode, SparkSession}

import java.io.UnsupportedEncodingException

/**
 *
 * @author yao.wang (vaio.MR.CN@GMail.com)
 * @date 2021-04-23
 */
object SparkUtil {

  val MAGIC_NUMBER = 10000000000L

  val spark =  SparkSession
    .builder()
    .master("local")
    .appName("test")
    .config("spark.debug.maxToStringFields", "1000")
    .enableHiveSupport()
    .getOrCreate()

  //  import spark.implicits._
  //  Seq((1)).toDF("name")

  def exec(sql: String) = {
    spark.sql(sql)
  }

  def select(db: String, table: String) = {
    val sql = "SELECT * FROM " + db + "." + table
    exec(sql)
  }

  def select(db: String, table: String, columns: String*) = {
    val fields = columns.mkString(", ")
    val sql = "SELECT " + fields + " FROM " + db + "." + table
    exec(sql)
  }

  def createDF(data: List[Row], schema: List[StructField]) = {
    spark.createDataFrame(spark.sparkContext.parallelize(data), StructType(schema))

  }

  def write(df: DataFrame, path: String) = {
    df.write.option("nullValue", null).mode(SaveMode.Overwrite).csv("hdfs://" + path)
  }


  def getSpark(name: String): SparkSession = {
    val spark = SparkSession
      .builder()
      .master("local")
      .appName(name)
      .config("spark.debug.maxToStringFields", "1000")
      .enableHiveSupport()
      .getOrCreate()

    // combine gg_seq and gg_rba
    spark.udf.register("getRBA", getRBA)
    spark.udf.register("extractFirstName", extractFirstName)
    spark.udf.register("getPlaceLength", getPlaceLength)
    spark.udf.register("formatPlace", formatPlace)
    spark
  }

  val getRBA = (gg_seq: Int, gg_rba: Int) => {
    var offset: Long = gg_rba
    if (gg_rba >= MAGIC_NUMBER) {
      offset = gg_rba % MAGIC_NUMBER
    }
    gg_seq * MAGIC_NUMBER + offset
  }

  val extractFirstName = (value: String) => {
    val lines = value.split("\n")
    if (lines.length >= 3) {
      lines(2).trim()
    }
    "placeholder"
  }

  val getPlaceLength = (place: String) => {
    val stripped = place.replace("\n", "").replace("\r", "")
    var bytes = 0
    try {
      bytes = stripped.getBytes("UTF-8").length
    } catch {
      case e: UnsupportedEncodingException => bytes = -1
    }
    bytes

  }

  val formatPlace = (place: String, maxLength: Int) => {
    val stripped = place.replace("\n", "").replace("\r", "")
    var bytes = stripped.getBytes("UTF-8").length
    val chars = stripped.length
    var msg = ""
    var outputStr = ""
    if (bytes <= maxLength) {
      stripped.trim()
    } else {
      bytes = 0
      var index = 0

      while (bytes <= maxLength && index < chars) {
        try {
          bytes += stripped.substring(index, index + 1).getBytes("UTF-8").length
        } catch {
          case e: UnsupportedEncodingException => msg = e.getMessage()
        }
        index += 1
      }
      if (msg != null) {

      }
      outputStr = stripped.substring(0, index - 1)
      outputStr.trim()

    }

  }


}
