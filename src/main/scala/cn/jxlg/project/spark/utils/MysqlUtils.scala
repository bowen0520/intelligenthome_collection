package cn.jxlg.project.spark.utils

import java.sql.{Connection, DriverManager}
import java.sql.PreparedStatement
import java.sql.ResultSet
import cn.jxlg.project.spark.domain.EnvironmentLog

object MysqlUtils {
  //获取链接
  val url = "jdbc:mysql://192.168.112.135:8017/bs?characterEncoding=utf-8&useSSL=true&serverTimezone=GMT"
  val user = "root"
  val passwd = "root"
  Class.forName("com.mysql.cj.jdbc.Driver")
  val conn = DriverManager.getConnection(url, user, passwd)

  def put(env:EnvironmentLog) =  {
    try {
      val ist = "insert intelligenthome(time,place,temple,hum,co2,light) value(?,?,?,?,?,?)"
      val preparedStatement: PreparedStatement = conn.prepareStatement(ist)
      //给占位符赋值
      preparedStatement.setString(1, env.time)
      preparedStatement.setString(2, env.place)
      preparedStatement.setString(3, env.temp)
      preparedStatement.setString(4, env.hum)
      preparedStatement.setString(5, env.co2)
      preparedStatement.setString(6, env.light)
      //statement.executeUpdate() int,执行DML
      val i: Int = preparedStatement.executeUpdate
    }catch {
      case e:Exception => {
        Log4jUtils.setErrLog(e.getMessage)
      }
    }
  }

  def main(args: Array[String]): Unit = {
      println(conn)
  }

}
