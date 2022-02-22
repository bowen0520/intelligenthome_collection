package cn.jxlg.project.spark.utils

import org.apache.log4j.Logger

object Log4jUtils {
  val logger = Logger.getLogger(Log4jUtils.getClass)

  def setInfoLog(msg:String)={
    logger.info(msg)
  }

  def setErrLog(msg:String)={
    logger.error(msg)
  }
}
