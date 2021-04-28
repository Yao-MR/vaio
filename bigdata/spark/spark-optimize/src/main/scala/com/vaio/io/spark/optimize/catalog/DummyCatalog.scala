package com.vaio.io.spark.optimize.catalog

import org.apache.spark.sql.connector.catalog.CatalogPlugin
import org.apache.spark.sql.util.CaseInsensitiveStringMap
/**
 *
 * @author yao.wang (Yao.MR.CN@GMail.com)
 * @date 2021-04-27
 */
class DummyCatalog extends CatalogPlugin{
  private var  _name  : String = null
  override def initialize(s: String, caseInsensitiveStringMap: CaseInsensitiveStringMap): Unit = {
 _name = s
  }
  override def name(): String = _name
  override def defaultNamespace(): Array[String] = Array("a", "b")
}
