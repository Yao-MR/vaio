package com.vaio.io.spark.common

import java.text.SimpleDateFormat

object DateUtil {

  var yesterday = ""
  var today = ""
  var todaySeconds = 0L
  var runDate = ""

  def parseDate(dt: String): Unit = {
    runDate = dt
    val todayDt = new SimpleDateFormat("yyyy-MM-dd").parse(dt)
    val fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    today = fmt.format(todayDt)
    todaySeconds = todayDt.getTime() / 1000
    yesterday = fmt.format(todayDt.getTime() - 24 * 3600 * 1000L)

  }
  def getCurrentSeconds(): Long = {
    System.currentTimeMillis() / 1000
  }

  def getCurrentDate(): String = {
    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis())
  }


  def buildGGClauseStr(today: String): String = {
    val todayMillis = new SimpleDateFormat("yyyy-MM-dd").parse(today).getTime()
    val fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val start = fmt.format(todayMillis - 24 * 3600 * 1000L)
    val end = fmt.format(todayMillis + 15 * 60 * 1000L)
    """
    WHERE
    GG_OP_TYPE <> 'DELETE'
    AND GG_BEFORE_AFTER = 'AFTER'
    AND GG_COMMIT_TIMESTAMP >= '""" + start + """'
    AND GG_COMMIT_TIMESTAMP <= '""" + end + """'
    """
  }
}
