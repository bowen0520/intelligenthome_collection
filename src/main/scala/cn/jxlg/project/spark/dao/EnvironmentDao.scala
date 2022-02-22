package cn.jxlg.project.spark.dao

import cn.jxlg.project.spark.domain.TempLog

import scala.collection.mutable.ListBuffer
/**
  * 访问数据库DAO层
  * 今天到现在为止的环境数据
  */
object EnvironmentDao {

  val tableName= "bs:intelligenthome"    //表名

  /**
    * 向数据库中保存数据
    * @param envList
    */
  def save(envList:ListBuffer[TempLog]): Unit ={

  }

  /**
    * 从数据中读取信息
    * @param time
    * @return
    */
  def get(time:String,place:String)={

  }
}
