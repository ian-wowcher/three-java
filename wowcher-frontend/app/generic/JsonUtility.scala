package generic

import scala.util.control.NonFatal

object JsonUtility {

  val jsonStringToInt = new CustomSerializer[Int](format => (
    {case JString(stuff) => stuff.toInt},
    PartialFunction.empty
    ))

  def extract[T <: AnyRef](json: String)(implicit mf: scala.reflect.Manifest[T]): T = {
    implicit val formats = DefaultFormats + jsonStringToInt
    try {
      parse(json).extract[T]
    } catch {
      case NonFatal(e: Exception) =>
        throw new ParseException(s"Failed to parse JSON for $mf due to $e:\n$json", e)
    }
  }

  def serialize[T<:AnyRef](value: T)(implicit mf: scala.reflect.Manifest[T]) = {
    implicit val formats = DefaultFormats
    parse(write(value))
  }

  def printPretty[T <: AnyRef](value: T)(implicit mf: scala.reflect.Manifest[T]): String = {
    pretty(render(serialize(value)))
  }

}