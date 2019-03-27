import org.apache.spark.sql.SparkSession
import org.apache.spark.SparkConf
import org.apache.log4j.{Level,Logger}

case class OneItem(date:String,action:String,source_ip:String,source_port:String,dest_ip:String,dest_port:String)

object StepOneV2 {
  def main(args: Array[String]) {
    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
    Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)

    if(args.length<2){
      println("Two params required !")
      return
    }
    val inputFile =  args(1)
    val conf = new SparkConf().setAppName("StepOne").setMaster(args(0))
    val sparkSession = SparkSession.builder().config(conf).getOrCreate()
    val sparkContext = sparkSession.sparkContext

    import sparkSession.implicits._

    val textFile = sparkContext.textFile(inputFile)

    val logDF = textFile.map(_.split(" ")).map(p=>OneItem(p(0),p(1),p(2),p(3),p(4),p(5))).toDF()

    logDF.createOrReplaceTempView("log")

    val display_rdd1 = sparkSession.sql("select source_ip,dest_ip from log where  dest_port=22").rdd
    display_rdd1.take(30).foreach(println)
    println("################################")
    val display_rdd2 = sparkSession.sql("select source_ip,dest_ip from log where  dest_port=80").rdd
    display_rdd2.take(30).foreach(println)
  }
}
