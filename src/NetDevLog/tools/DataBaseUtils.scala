package tools

import java.sql.{Connection, DriverManager, PreparedStatement}

object DataBaseUtils {

  val jdbc_url = "jdbc:mysql://localhost:3306/"
  val jdbc_user = "root"
  val jdbc_password = ""

  var connection:Connection = null
  var stmt:PreparedStatement = null

  def execute(cmd:String):Unit={
    try {
      val connection = DriverManager.getConnection(jdbc_url, jdbc_user, jdbc_password)
      val stmt = connection.createStatement()
      stmt.execute(cmd)
    }catch{
      case e:Exception => e.printStackTrace()
    }finally {
      if(stmt!=null){
        connection.close()
      }
      if(connection!=null){
        connection.close()
      }
    }
  }

  def createDatabase():Unit={
    val cmd:String = "create database if not exists spark"
    execute(cmd)
  }

  def deleteDatabase():Unit={
    val cmd:String = "drop database if exists spark"
    execute(cmd)
  }

  def createTable():Unit={
    val cmd:String =
      """
        create table spark.result
        (
          id integer not null primary key auto_increment,
          date text,
          action text,
          source_ip text,
          source_port text,
          dest_ip text,
          dest_port text
        )
      """
    execute(cmd)
  }

  def deleteTable():Unit={
    val cmd:String =
      """
        drop table if exists spark.result
      """.stripMargin
    execute(cmd)
  }

  def run():Unit={
    //deleteDatabase()
    createDatabase()
    deleteTable()
    createTable()
  }

  def main(args:Array[String]): Unit ={
    run()
  }
}
