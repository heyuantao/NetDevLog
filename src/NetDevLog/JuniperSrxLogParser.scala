import scala.util.matching.Regex

class JuniperSrxLogParser extends Serializable {

  val session_create_string = "session created"
  val session_close_string = "session closed unset:"
  val session_create_pattern = new Regex(".* session created (.*) .*")
  val session_close_pattern = new Regex(".* session closed unset: (.*) .*")

  def isValideLine(raw_string:String): Boolean = {
    var final_status:Boolean = false

    if(raw_string.contains(session_create_string)){
      final_status = true
    }else if(raw_string.contains(session_close_string)){
      final_status = true
    }else{
      final_status = false
    }

    final_status
  }

  def getTimeAndIPInformationFromLine(raw_string:String) :String={
    var time:String = ""
    var sip:String = ""
    var dip:String = ""
    var sport:String = ""
    var dport:String = ""
    var action:String = ""
    var return_string:String = ""

    if(raw_string.contains(session_close_string)){
      var matched_string = raw_string match { case session_close_pattern(bc)=> bc }
      var matched_string_part = matched_string.split(" ")
      val ip_part = matched_string_part(0).split("->")
      time = raw_string.split(" ")(0)
      sip = ip_part(0).split("/")(0)
      sport = ip_part(0).split("/")(1)
      dip = ip_part(1).split("/")(0)
      dport = ip_part(1).split("/")(1)
      action = "create"
      return_string = time+" "+action+" "+sip+" "+sport+" "+dip+" "+dport
    }
    else if (raw_string.contains(session_create_string)){
      var matched_string = raw_string match { case session_create_pattern(bc)=> bc }
      var matched_string_part = matched_string.split(" ")
      val ip_part = matched_string_part(0).split("->")
      time = raw_string.split(" ")(0)
      sip = ip_part(0).split("/")(0)
      sport = ip_part(0).split("/")(1)
      dip = ip_part(1).split("/")(0)
      dport = ip_part(1).split("/")(1)
      action = "close"
      return_string = time+" "+action+" "+sip+" "+sport+" "+dip+" "+dport
    }else{
      return_string = ""
    }

    return_string
  }
}
