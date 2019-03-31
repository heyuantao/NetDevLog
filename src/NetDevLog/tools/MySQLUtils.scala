package tools
import java.sql.{Connection, DriverManager, PreparedStatement}
import org.apache.spark.sql.{DataFrame, SparkSession}


object MySQLUtils {
  val connection_url = "jdbc:mysql://localhost:3306/spark?user=root"
  /*
  get the database connection
   */
  def getConnection() ={
    DriverManager.getConnection(connection_url)
  }
  /*
    Close the database connection
   */
  def release(connection:Connection,pstmt:PreparedStatement):Unit={
    try{
      if(pstmt!=null){
        pstmt.close()
      }
    }catch{
      case e:Exception => e.printStackTrace()
    }finally {
      if(connection!=null){
        connection.close()
      }
    }
  }
  /*
  Function for test
   */
  def main(args:Array[String]){
    val spark_session = SparkSession.builder().appName("mysql").master("local").getOrCreate()

  }
}
