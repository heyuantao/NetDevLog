import org.apache.spark.sql.SparkSession
import org.apache.spark.SparkConf
import org.apache.log4j.{Level,Logger}
import org.apache.spark.sql.Row
import org.apache.spark.sql.types.{StructType,StructField,StringType}

case class Record(date:String,action:String,source_ip:String,source_port:String,dest_ip:String,dest_port:String)

object StepOne {
  def main(args: Array[String]) {
    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
    Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF)

    if(args.length<2){
      println("Two params required !")
      return
    }
    val inputFile =  args(1)
    val conf = new SparkConf().setAppName("StepOne").setMaster(args(0))
    //val sparkContext = new SparkContext(conf)
    val sparkSession = SparkSession.builder().config(conf).getOrCreate()
    //import sparkSession.implicits._
    val sparkContext = sparkSession.sparkContext

    val schemaString = "data action source_ip source_port dest_ip dest_port"
    val schema = StructType(schemaString.split(" ").map(fieldName => StructField(fieldName, StringType)))
    val textFile = sparkContext.textFile(inputFile)

    val rowRDD = textFile.map(_.split(" ")).map(p=>Row(p(0),p(1),p(2),p(3),p(4),p(5)))
    val logDataFrame = sparkSession.createDataFrame(rowRDD, schema)

    logDataFrame.createOrReplaceTempView("log")

    val display_rdd = sparkSession.sql("select source_ip,dest_ip from log where  dest_port=22").rdd
    display_rdd.take(10).foreach(println)

    println(logDataFrame.printSchema())
  }
}
