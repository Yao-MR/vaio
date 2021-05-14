package com.vaio.io.spark.sql;

import java.io.IOException;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.apache.spark.sql.catalyst.parser.SqlBaseLexer;
import org.apache.spark.sql.catalyst.parser.SqlBaseParser;

/**
 * @author yao.wang (Yao.MR.CN@GMail.com)
 * @date 2021-05-13
 */
public class PraseDriver {

  public static void main(String[] args) throws IOException {
    String query = "select * from tab";
    SqlBaseLexer lexe = new SqlBaseLexer(new ANTLRInputStream(query.toUpperCase()));
    SqlBaseParser parser = new SqlBaseParser(new CommonTokenStream(lexe));
    MyVisitor visitor = new MyVisitor();
    visitor.visitSingleStatement(parser.singleStatement());
  }
}
