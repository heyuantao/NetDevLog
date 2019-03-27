import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf
import org.apache.log4j.{Level,Logger}

object Preprocess {
  def main(args: Array[String]) {
    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
    Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)

    if(args.length<3){
      println("Three params required !")
      return
    }
    val inputFile =  args(1)
    val outputFile = args(2)
    val conf = new SparkConf().setAppName("LogInsight").setMaster(args(0))

    val sc = new SparkContext(conf)
    val textFile = sc.textFile(inputFile)
    val parser = new JuniperSrxLogParser()
    val lines = textFile.filter(parser.isValideLine(_)).map(line=>parser.getTimeAndIPInformationFromLine(line))

    lines.take(10).foreach(println)
    lines.saveAsTextFile(outputFile)
  }
}
