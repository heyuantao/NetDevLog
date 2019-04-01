package tools

import java.util.Properties
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.functions._

object ReadJOL {

  def setLogLevel():Unit={
    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
    Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.WARN)
  }

  def main(args: Array[String]): Unit = {
    setLogLevel()
    val output_file = "file:///d:/jol/solution"
    val spark_session = SparkSession.builder().master("local").appName("readOJ")
      .getOrCreate()
    val spark_context = spark_session.sparkContext

    var connectionProperties = new Properties();
    connectionProperties.put("driver", "com.mysql.jdbc.Driver");
    connectionProperties.put("user", "root");

    val df = spark_session.read.jdbc("jdbc:mysql://localhost:3306/","jol.solution",connectionProperties)
    df.show(20)
    df.write.mode("Overwrite").format("parquet").save(output_file)

    spark_session.stop()
  }
}
