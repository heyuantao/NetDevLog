import org.apache.spark.sql.SparkSession
import org.apache.spark.SparkConf
import org.apache.log4j.{Level,Logger}

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
    val spark_context = spark_session.sparkContext

    val textFile = spark_context.textFile(input_file)

    val lines = textFile.map(line=>line).filter(_.length>0)
    //lines.saveAsTextFile("file:///d:/srx240h-2019.03.output")
    println(lines.count())
    //lines.foreach(println)
  }
}