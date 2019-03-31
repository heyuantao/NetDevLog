import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.functions._

object Analyze {

  def main(args: Array[String]) {
    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
    Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.WARN)

    if(args.length<2){
      println("Two params require !")
      return
    }

    val input_file =  args(1)
    val spark_session = SparkSession.builder().appName("Analyze").master(args(0))
      .getOrCreate()

    val dataframe = spark_session.read.format("parquet").load(input_file)

    println("Query Result  !")
    topAccess(spark_session,dataframe)
    spark_session.stop()
  }

  def topAccess(sc:SparkSession,df:DataFrame): Unit ={
    df.createOrReplaceTempView("access")
    val query_cmd="""
      select source_port,count(source_port) from access where dest_ip='151.101.228.167' group by source_port
        order by source_port
      """
    //val query_cmd = "select * from access where dest_ip='151.101.228.167' "
    val result = sc.sql(query_cmd)
    result.show(false)
  }

}