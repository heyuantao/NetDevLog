import org.apache.spark.sql.SparkSession
import org.apache.log4j.{Level, Logger}
import tools.JuniperSrxLogParser

object Preprocess {
  def main(args: Array[String]) {
    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
    Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)

    if(args.length<3){
      println("Three params required !")
      return
    }
    val input_file =  args(1)
    val output_file = args(2)
    val spark_session = SparkSession.builder().appName("Preprocess").master(args(0)).getOrCreate()
    val spark_context = spark_session.sparkContext

    val textFile = spark_context.textFile(input_file)

    val row_lines = textFile.filter(JuniperSrxLogParser.isValideLine(_)).map(line=>JuniperSrxLogParser.getTimeAndIPInformationFromLine(line))

    val dataframe = spark_session.createDataFrame(row_lines.map(x=>JuniperSrxLogParser.toRow(x)),JuniperSrxLogParser.getStruct())
    //.coalesce(1)

    dataframe.write.mode("Overwrite").format("parquet").save(output_file)

    //dataframe.take(10).foreach(println)
    dataframe.printSchema()
    dataframe.show(20)

    spark_session.close()
  }
}
