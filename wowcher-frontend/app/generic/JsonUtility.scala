package generic

import org.json4s.CustomSerializer
import org.json4s.JsonAST.JString
import org.json4s.ParserUtil.ParseException

import scala.util.control.NonFatal

object JsonUtility {

  val jsonStringToInt = new CustomSerializer[Int](format => (
    {case JString(stuff) => stuff.toInt},
    PartialFunction.empty
    ))

  def extract[T <: AnyRef](json: String)(implicit mf: scala.reflect.Manifest[T]): T = {
    import org.json4s._
    import org.json4s.jackson.JsonMethods._
    implicit val formats = DefaultFormats + jsonStringToInt
    try {
      parse(json).extract[T]
    } catch {
      case NonFatal(e: Exception) =>
        throw new ParseException(s"Failed to parse JSON for $mf due to $e:\n$json", e)
    }
  }

  def serialize[T<:AnyRef](value: T)(implicit mf: scala.reflect.Manifest[T]) = {
    import org.json4s._
    import org.json4s.jackson.JsonMethods._
    import org.json4s.jackson.Serialization._
    implicit val formats = DefaultFormats
    parse(write(value))
  }

  def printPretty[T <: AnyRef](value: T)(implicit mf: scala.reflect.Manifest[T]): String = {
    import org.json4s.jackson.JsonMethods._
    pretty(render(serialize(value)))
  }

}