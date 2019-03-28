import java.text.SimpleDateFormat
import java.util.Date

object DateUtils {
  val source_date_format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX") //UTC Date format
  val dest_data_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  def parse(time:String):String={
    val get = getTime(time)
    val da = new Date(get)
    val res = dest_data_format.format(da)
    res
  }

  def getTime(time:String)={
    try{
      source_date_format.parse(time).getTime
    }catch {
      case e:Exception =>{
        0l
      }
    }
  }

  def main(args:Array[String]): Unit ={
    println(parse("2019-03-01T00:00:01.751Z"))
  }
}
