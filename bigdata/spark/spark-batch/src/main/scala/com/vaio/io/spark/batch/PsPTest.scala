package com.vaio.io.spark.batch

import com.vaio.io.spark.common.DateUtil
import com.vaio.io.spark.core.SparkUtil
import org.apache.spark.sql.functions.{lit, when}

/**
 *
 * @author yao.wang (vaio.MR.CN@GMail.com)
 * @date 2021-04-23
 */
object PsPTest {
  def main(arg: Array[String]): Unit = {
    val args = new Array[String](2)
    args(0) = "2020-01-02"
    args(1) = "/hdfs"

    if (args.length != 2) {
      throw new IllegalArgumentException("requires 2 arguments")
    }
    val today = args(0)
    val savePath = args(1)
    val spark = SparkUtil.getSpark("Cust Name ETL")

    val partyName = spark.read.format("csv")
      .option("sep", "\t")
      .option("inferSchema", "true")
      .option("header", "true")
      .load("/Users/ywang46/WorkSpace/intell/vaio/bigdata/spark/spark-core/src/main/resources/CONFDBA.PARTY_NAME.csv")
    partyName.createOrReplaceTempView("PARTY_NAME")





    partyName.show(10)


    /** **
     *
     * val partyNameExtract = spark.sql(
     * """
     * SELECT
     * ID,
     * PARTY_ID,
     * GIVEN_NAME,
     * MIDDLE_NAME,
     * SURNAME,
     * ALTERNATE_FULL_NAME,
     * UPDATE_VERSION,
     * SECONDARY_SURNAME
     * FROM
     * (SELECT
     * GG_COMMIT_TIMESTAMP,
     * ID,
     * PARTY_ID,
     * GIVEN_NAME,
     * MIDDLE_NAME,
     * SURNAME,
     * ALTERNATE_FULL_NAME,
     * TIME_CREATED,
     * TIME_UPDATED,
     * UPDATE_VERSION,
     * SECONDARY_SURNAME,
     * ROW_NUMBER() OVER(PARTITION BY ID ORDER BY GG_RBA DESC) RANK
     * FROM(
     * SELECT
     * GG_COMMIT_TIMESTAMP,
     * getRBA(GG_TRAIL_SEQ, GG_TRAIL_RBA) AS GG_RBA,
     * ID,
     * PARTY_ID,
     * GIVEN_NAME,
     * MIDDLE_NAME,
     * SURNAME,
     * ALTERNATE_FULL_NAME,
     * TIME_CREATED,
     * TIME_UPDATED,
     * UPDATE_VERSION,
     * SECONDARY_SURNAME
     * FROM GG_PARTY_NAME
     * """ + DateUtil.buildGGClauseStr(today) +
     * """) A
     * ) B
     * WHERE B.RANK = 1
     * """
     * )
     *
     * val partyType = spark.sql(
     * """
     * SELECT
     * PARTY_TYPE,
     * PARTY_ID
     * FROM PARTYV2
     * WHERE PARTY_TYPE='PERSON' OR PARTY_TYPE='BUSINESS'
     * """)
     *
     * val partyPersonTag = spark.sql(
     * """
     * SELECT
     * PARTY_NAME_ID,
     * TAG,
     * PARTY_ID
     * FROM PARTY_NAME_TAG
     * WHERE UPPER(TAG)='ALIAS'
     * """)
     *
     * val partyBusinessTag = spark.sql(
     * """
     * SELECT
     * PARTY_NAME_ID,
     * TAG,
     * PARTY_ID
     * FROM PARTY_NAME_TAG
     * WHERE
     * UPPER(TAG)='ALIAS'
     * AND PARTY_NAME_ID NOT IN (SELECT PARTY_NAME_ID FROM PARTY_NAME_TAG WHERE
     * UPPER(TAG)='DBA')
     * """)
     *
     * val personAcccount = spark.sql(
     * """
     * SELECT
     * account_number
     * FROM
     * (SELECT
     * Account_party_id,
     * account_number,
     * ROW_NUMBER() OVER(PARTITION BY account_number ORDER BY Account_party_id DESC) RANK
     * FROM ACCOUNT_PARTY_PRIMARY) A
     * WHERE RANK = 1
     * """)
     *
     * val businessAccount = spark.sql(
     * """
     * SELECT
     * ACCOUNT_NUMBER,
     * PARTY_ID
     * (SELECT
     * ACCOUNT_NUMBER,
     * PARTY_ID,
     * ROW_NUMBER() OVER(PARTITION BY PARTY_ID ORDER BY ACCOUNT_NUMBER DESC) RANK
     * FROM PARTY_ACCOUNT) A
     * WHERE RANK = 1
     * """)
     *
     * val customers = spark.sql(
     * """
     * SELECT
     * CUSTOMER_ID,
     * ACCOUNT_NUMBER
     * FROM
     * (
     * select CUSTOMERS.ID CUSTOMER_ID,
     * ACCOUNT_NUMBER,
     * ROW_NUMBER() OVER(PARTITION BY ACCOUNT_NUMBER ORDER BY ID DESC) RANK
     * from CUSTOMERS
     * where CUSTOMERS.account_flag not in('C','I','X')
     * and CUSTOMERS.role_code is null
     * and CUSTOMERS.source_id is null) A
     * WHERE RANK = 1
     * """)
     *
     * val customerName = spark.sql(
     * """
     * NAME_ID,
     * CUSTOMER_ID,
     * PARTY_ID,
     * FIRST_NAME,
     * MIDDLE_NAME,
     * LAST_NAME,
     * BUSINESS_NAME,
     * SOURCE_ID
     * FROM CUSTOMER_NAME
     * """)
     *
     * val custNameWithType = partyNameExtract
     * .join(partyType, partyNameExtract("PARTY_ID") === partyType("PARTY_ID"), "inner")
     * .drop(partyType("PARTY_ID"))
     *
     * val custNameWithPersonTag = custNameWithType
     * .join(partyPersonTag, custNameWithType("ID") === partyPersonTag("PARTY_NAME_ID"), "inner")
     * .where("PARTY_TYPE='PERSON").drop(partyPersonTag("PARTY_ID"))
     * val custNameWithBusinessTag = custNameWithType
     * .join(partyBusinessTag, custNameWithType("ID") === partyBusinessTag("PARTY_NAME_ID"), "inner")
     * .where("PARTY_TYPE='BUSINESS")
     * .drop(partyBusinessTag("PARTY_ID"))
     *
     * val custNameWithPersonAccount = custNameWithPersonTag
     * .join(personAcccount, custNameWithPersonTag("PARTY_ID") === personAcccount("account_number"), "inner")
     * val custNameWithBusinessAccount = custNameWithBusinessTag
     * .join(businessAccount, custNameWithBusinessTag("PARTY_ID") === businessAccount("PARTY_ID"), "inner")
     * .drop(businessAccount("PARTY_ID"))
     *
     * val custInfo = custNameWithPersonAccount
     * .union(custNameWithBusinessAccount)
     * .selectExpr(
     * """
     * ACCOUNT_NUMBER IN_ACCOUNT_NUMBER,
     * PARTY_ID,
     * ID,
     * GIVEN_NAME,
     * MIDDLE_NAME,
     * SURNAME,
     * ALTERNATE_FULL_NAME,
     * UPDATE_VERSION,
     * SECONDARY_SURNAME,
     * PARTY_TYPE
     * """)
     *
     * val customer = custInfo
     * .join(customers, custInfo("IN_ACCOUNT_NUMBER") === customers("ACCOUNT_NUMBER"), "inner")
     * .filter(customers("CUSTOMER_ID").isNotNull)
     * .withColumn("NAME_TYPE", when(custInfo("PARTY_TYPE") === "PERSON", "A").otherwise(when(custInfo("PARTY_TYPE") === "BUSINESS", "B")))
     * .withColumn("o_BUSINESS_NAME", when(custInfo("PARTY_TYPE") === "BUSINESS", custInfo("ALTERNATE_FULL_NAME")).otherwise(when(custInfo("PARTY_TYPE") === "PERSON", "")))
     * .withColumn("SYSDATE", lit(DateUtil.getCurrentSeconds()))
     * .withColumn("UNIX_TIME", lit(DateUtil.getCurrentSeconds())) //时区转化
     * .drop(custInfo("PARTY_TYPE"))
     * .drop(custInfo("ALTERNATE_FULL_NAME"))
     * .withColumnRenamed("CUSTOMER_ID", "IN_CUSTOMER_ID")
     * .withColumnRenamed("PARTY_ID", "IN_PARTY_ID")
     *
     * /**
     * val customerRouter = customer
     * .join(customerName, customer("IN_CUSTOMER_ID") === customerName("CUSTOMER_ID") && customer("IN_PARTY_ID") === customerName("PARTY_ID") && customer("PARTY_NAME_ID") === customerName("SOURCE_ID"), "left")
     * .withColumnRenamed("", "")
     * .withColumn("IGNORE", lit(DateUtil.getCurrentSeconds())) //时区转化//"NAME_ID iS NULL OR (IIF(LTRIM(RTRIM(FIRST_NAME3))=LTRIM(RTRIM(GIVEN_NAME3)) AND LTRIM(RTRIM(MIDDLE_NAME4))=LTRIM(RTRIM(MIDDLE_NAME13)) AND LTRIM(RTRIM(LAST_NAME3))=LTRIM(RTRIM(SURNAME3)) AND LTRIM(RTRIM(BUSINESS_NAME3))=LTRIM(RTRIM(BUSINESS_NAME43)) ,'N','U') == U)")
     * .select(
     * """
     * NAME_ID,
     * CUSTOMER_ID,
     * PARTY_ID,
     * FIRST_NAME,
     * MIDDLE_NAME,
     * LAST_NAME,
     * BUSINESS_NAME,
     * IN_CUSTOMER_ID,
     * ACCOUNT_NUMBER,
     * IN_ACCOUNT_NUMBER,
     * PARTY_NAME_ID,
     * IN_PARTY_ID,
     * GIVEN_NAME,
     * MIDDLE_NAME IN_MIDDLE_NAME,
     * SURNAME,
     * UPDATE_VERSION,
     * SECONDARY_SURNAME,
     * NAME_TYPE,
     * SYS_DATE,
     * UNIX_TIME,
     * o_BUSINESS_NAME,
     * IGNORE
     * """.stripMargin)
     * customerRouter
     * .show(5)
     *
     * val CustNameInsert = customerRouter
     * .select("NAME_ID", "IN_CUSTOMER_ID AS CUSTOMER_ID", "PARTY_ID", "FIRST_NAME", "MIDDLE_NAME", "LAST_NAME", "NAME_TYPE", "PARTY_NAME_ID AS SOURCE_ID", "TIME_CREATED", "TIME_CREATED TIME_UPDATED", "o_BUSINESS_NAME BUSINESS_NAME")
     *
     * val CustNameUpdate = customerRouter
     * .filter("IGNORE === U")
     * .select("NAME_ID", "GIVEN_NAME FIRST_NAME", "IN_MIDDLE_NAME MIDDLE_NAME", "FIRST_NAME", "MIDDLE_NAME", "SURNAME LAST_NAME", "UNIX_TIME TIME_UPDATED", "o_BUSINESS_NAME BUSINESS_NAME")
     * val update = customerRouter
     * .selectExpr("IN_CUSTOMER_ID AS CUSTOMER_ID,ACCOUNT_NUMBER")
     * .union(
     * customerRouter.filter("IGNORE === U").selectExpr("IN_CUSTOMER_ID AS CUSTOMER_ID，IN_ACCOUNT_NUMBER ACCOUNT_NUMBER")
     * )
     * .withColumn("Need_scan", lit("Y"))
     * .withColumn("NAME_VERIFICATION_NEED_SCAN", lit("Y"))
     * .withColumn("updated_date", lit("Y"))
     * */
     * }
     * */
  }
}