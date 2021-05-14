package com.vaio.io.spark.sql;

import org.apache.spark.sql.catalyst.parser.SqlBaseBaseVisitor;
import org.apache.spark.sql.catalyst.parser.SqlBaseParser;

/**
 * @author yao.wang (Yao.MR.CN@GMail.com)
 * @date 2021-05-13
 */
public class MyVisitor extends SqlBaseBaseVisitor<String> {

  public String visitSingleStatement(SqlBaseParser.SingleStatementContext ctx) {
    System.out.println("这里访问了一个节点");
    return visitChildren(ctx);
  }
}
