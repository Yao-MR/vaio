// scalastyle:off
package com.vaio.io.spark.optimize

import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.sources.{BaseRelation, DataSourceRegister, RelationProvider, SchemaRelationProvider}
import org.apache.spark.sql.types.StructType

/**
 * 背景:
 * 思路:
 * 算法:
 * 参考:
 *
 * @author yao.cn.mr@gmail.com
 * @date 2021-01-28
 */
class ExternalRelationProvider extends RelationProvider with SchemaRelationProvider with DataSourceRegister {
  override def createRelation(sqlContext: SQLContext, parameters: Map[String, String]): BaseRelation = {
    createRelation(sqlContext,null,null)
  }

  override def createRelation(sqlContext: SQLContext, parameters: Map[String, String], schema: StructType): BaseRelation = {
    PrintRelation(schema)(sqlContext.sparkSession)
  }

  override def shortName(): String = "printdb"
}
