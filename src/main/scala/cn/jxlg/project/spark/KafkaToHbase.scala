package cn.jxlg.project.spark

import cn.jxlg.project.spark.domain.{EnvironmentLog, TempLog}
import cn.jxlg.project.spark.utils.{DateUtils, Log4jUtils, MysqlUtils}
import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}
import kafka.serializer.StringDecoder

object KafkaToHbase {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org.apache.spark").setLevel(Level.ERROR)
    Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)
    //构建conf ssc 对象
    val conf = new SparkConf().setAppName("Env").setMaster("local[2]")
    val ssc = new StreamingContext(conf, Seconds(15))
    //kafka 需要Zookeeper  需要消费者组
    val topics = Set("intelligenthome")
    //broker的原信息     ip地址以及端口号
    val kafkaPrams = Map[String, String]("metadata.broker.list" -> "192.168.112.133:9092")
    // 数据的输入类型    数据的解码类型
    val data = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](ssc, kafkaPrams, topics)
    //统计结果
    val result = data
      .map(_._2)
      .map(line => {
        Log4jUtils.setInfoLog("加载一条kafka数据" + line)
        val datas = line.split("[|]")
        //datas.apply(8).toLong datas(8)
        val time = DateUtils.getTime(datas(8).toLong)
        TempLog(time,datas(0),datas(3),datas(6),datas(7))})
      .filter(tempLog =>tempLog.state.equals("1"))  //状态标识为1的为有效数据

    result.map(tempLog =>{
      (tempLog.time,tempLog)
    }).groupByKey().foreachRDD(rdd =>{
      rdd.foreach(msg =>{
        val time:String = msg._1
        var place:String = null
        var temp:String = null
        var hum:String = null
        var co2:String = null
        var light:String = null
        for(env <- msg._2){
          place = env.place
          if(env.tp.equals("16")){
            temp = ((DateUtils.getData(env.data.substring(0,4)) * 0.00268127) - 46.85).formatted("%.2f")
            hum = ((DateUtils.getData(env.data.substring(4,8)) * 0.00190735) - 6).formatted("%.2f")
          }else if(env.tp.equals("256")){
            light = DateUtils.getData(env.data.substring(0,4)).toString
          }else if(env.tp.equals("1280")){
            co2 = DateUtils.getData(env.data.substring(0,4)).toString
          }
        }

        MysqlUtils.put(EnvironmentLog(time,place,temp,hum,co2,light))
        Log4jUtils.setInfoLog("插入一条数据："+time+" "+place)
      })
    })


    //启动程序
    ssc.start()
    Log4jUtils.setInfoLog("程序启动")
    ssc.awaitTermination()
  }
}
