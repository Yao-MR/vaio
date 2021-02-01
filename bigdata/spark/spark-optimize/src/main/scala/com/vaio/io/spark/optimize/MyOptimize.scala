// scalastyle:off
package com.vaio.io.spark.optimize

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.catalyst.analysis.UnresolvedRelation
import org.apache.spark.sql.catalyst.plans.logical.{InsertIntoStatement, LogicalPlan}
import org.apache.spark.sql.execution.QueryExecution
/**
 * 背景:
 *      解析spark的sql的整个逻辑执行计划
 * 思路:
 *      通过代码的形式来进行全程跟踪整个执行计划
 * 算法:
 *
 * 参考:
 *
 * @author yao.cn.mr@gmail.com
 * @date 2021-01-28
 */
object MyOptimize {
  def main(args: Array[String]) {
    val session = SparkSession.builder()
      .appName("MyOptimize")
      .getOrCreate()
    session.createDataFrame(Seq(
      ("ming", 20, 15552211521L),
      ("hong", 19, 13287994007L),
      ("zhi", 21, 15552211523L)
    )).toDF("name", "age", "phone").createOrReplaceTempView("table")


    val sqlText = "select name, age, phone from table";
    val sqlInsert = "INSERT OVERWRITE TABLE print select name, age, phone from table"
    val logicPlan = session.sessionState.sqlParser.parsePlan(sqlInsert);
    System.out.println("逻辑执行计划-------------------")
    System.out.println(logicPlan.toJSON)
    System.out.println("逻辑执行计划-------------------")

    logicPlan match {
      case InsertIntoStatement(table: LogicalPlan, _, child: LogicalPlan, _, _) =>
        val ddl = new QueryExecution(session, child).analyzed.schema.toDDL
        val realtionName = table.asInstanceOf[UnresolvedRelation].name;
        System.out.println(ddl)
        System.out.println(realtionName)
        var createViewSql = s"""
           |CREATE OR REPLACE TEMPORARY VIEW ${realtionName} (${ddl})
           |USING printdb
           |""".stripMargin
        session.sql(createViewSql)
    }

    var df = session.sql(sqlText)
    df.rdd.foreachPartition(x => {
      while (x.hasNext) {
        System.out.println("-----------------开始---------------")
        System.out.println((x.next().toString()))
        System.out.println("-----------------结束------------")
      }
    })
  }
}


