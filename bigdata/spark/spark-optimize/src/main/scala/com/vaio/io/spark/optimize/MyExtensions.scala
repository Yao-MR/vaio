// scalastyle:off
package com.vaio.io.spark.optimize

import org.apache.spark.internal.Logging
import org.apache.spark.sql.catalyst.expressions.{Literal, Multiply}
import org.apache.spark.sql.{SparkSession, SparkSessionExtensions}
import org.apache.spark.sql.catalyst.plans.logical.LogicalPlan
import org.apache.spark.sql.catalyst.rules.Rule
import org.apache.spark.sql.types.Decimal

/**
 *
 * @author yao.cn.mr@gmail.com
 * @date 2021-01-28
 */
case class MyRule(spark: SparkSession) extends Rule[LogicalPlan] with Logging {
  override def apply(plan: LogicalPlan): LogicalPlan = {
    logWarning("开始应用 MyRule 优化规则")
    plan transformAllExpressions {
      case Multiply(left, right, false) if right.isInstanceOf[Literal] &&
        right.asInstanceOf[Literal].value.isInstanceOf[Decimal] &&
        right.asInstanceOf[Literal].value.asInstanceOf[Decimal].toDouble == 1.0 =>
        logWarning("MyRule 优化规则生效")
        left
    }
  }
}

class MyExtensions extends (SparkSessionExtensions => Unit) with Logging {
  def apply(e: SparkSessionExtensions): Unit = {
    logWarning("进入MyExtensions扩展点")
    e.injectResolutionRule(MyRule)
  }
}