package com.vaio.io.spark.optimize.catalog

import com.vaio.io.spark.optimize.datasource.CustomTable
import org.apache.spark.internal.Logging
import org.apache.spark.sql.connector.catalog.{CatalogPlugin, DelegatingCatalogExtension}
import org.apache.spark.sql.connector.catalog.{DelegatingCatalogExtension, Identifier, Table}

/**
 *
 * @author yao.wang (Yao.MR.CN@GMail.com)
 * @date 2021-04-27
 */
class CustomCatalog extends DelegatingCatalogExtension with Logging {

  override def name(): String = "CUSTOM"

  override def loadTable(ident: Identifier): Table = CustomTable(ident)
