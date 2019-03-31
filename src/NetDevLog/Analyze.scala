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

    //dataframe.printSchema()
    //dataframe.show(false)
    println("Top access !")
    topAccess(spark_session,dataframe)
    spark_session.stop()
  }

  def topAccess(sc:SparkSession,df:DataFrame): Unit ={
    import sc.implicits._

    //val number01 = df.select("dest_ip").agg(count("dest_ip"))
    //val number02 = df.filter($"dest_ip" === "151.101.228.167").agg(count("dest_ip"))
    //number01.show(false)
    //number02.show(false)
    df.createOrReplaceTempView("access")
    val result = sc.sql("select count(*) from access where dest_ip='151.101.228.167' ")
    result.show(false)
  }

}