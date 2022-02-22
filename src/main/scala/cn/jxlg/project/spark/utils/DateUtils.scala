package cn.jxlg.project.spark.utils

import java.util.Date

import org.apache.commons.lang3.time.FastDateFormat

/**
  * 时间格式转换工具类
  */

object DateUtils {

  val OLD_FORMAT=FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss")
  val TARGET_FORMAT=FastDateFormat.getInstance("yyyyMMddHHmmss")

  /**
    * 字符串类型时间转换为时间戳
    * @param time
    * @return
    */
  def getTime(time:String) ={
    OLD_FORMAT.parse(time).getTime()
  }

  /**
    * 将字符串类型时间转换为新字符串格式
    * @param time
    * @return
    */
  def parseTime(time:String) ={
    TARGET_FORMAT.format(new Date(getTime(time)))
  }

  def getTime(time:Long) ={
    TARGET_FORMAT.format(time)
  }

  def getData(data:String)={
    java.lang.Integer.parseInt(data,16)
  }

  def main(args: Array[String]): Unit = {

    //"2018-07-23 12:23:01"
    //1532319781000
    val result=getTime("2018-07-23 12:23:01")
    println(result)
  }

}
