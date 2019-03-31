package tools
import java.sql.DriverManager

object MySQLUtils {
  val connection_url = "jdbc:mysql://localhost:3306/spark?user=root"
  def getConnection(): Unit ={
    DriverManager.getConnection(connection_url)
  }
  def main(args:Array[String]){
    val con= getConnection()
    println(con)
  }
}
