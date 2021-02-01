// scalastyle:off
package com.vaio.io.spark.optimize

import org.apache.spark.internal.Logging
import org.apache.spark.sql.sources.{BaseRelation, InsertableRelation}
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.{DataFrame, SQLContext, SparkSession}

/**
 * 背景:
 * 思路:
 * 算法:
 * 参考:
 *
 * @author yao.cn.mr@gmail.com
 * @date 2021-01-28
 */
case class PrintRelation(override val schema: StructType)(@transient val sparkSession: SparkSession)
extends BaseRelation with InsertableRelation with Logging {

  override def sqlContext: SQLContext = sparkSession.sqlContext
  override val needConversion: Boolean = false
  override def insert(data: DataFrame, overwrite: Boolean): Unit = {
    val rddSchema = data.schema;
    data.rdd.foreachPartition(iterator =>
    while(iterator.hasNext){
      val  row = iterator.next();
      logInfo(row.toString());
    }
    )
  }

  override def toString: String = {
    "----------------"
    }
}
