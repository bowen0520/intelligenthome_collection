package cn.jxlg.project

object TestVersion {
  def main(args: Array[String]): Unit = {
   val version= scala.util.Properties.releaseVersion
    println(version)
  }

}
