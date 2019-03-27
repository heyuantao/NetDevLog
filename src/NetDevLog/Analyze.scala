import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf
import org.apache.spark.SparkConf
import scala.util.matching.Regex
import org.apache.log4j.{Level,Logger}

object Analyze {
  def main(args: Array[String]) {
    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
    Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.WARN)

    if(args.length<2){
      println("Two params require !")
      return
    }
    val inputFile =  args(1)
    val conf = new SparkConf().setAppName("SrxLog").setMaster(args(0))
    val sc = new SparkContext(conf)
    val textFile = sc.textFile(inputFile)

    val lines = textFile.map(line=>line).filter(_.length>0)
    //lines.saveAsTextFile("file:///d:/srx240h-2019.03.output")
    println(lines.count())
    //lines.foreach(println)
  }
}