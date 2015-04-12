package generic

import java.net.URLEncoder

/**
 * Created on 22/08/2014.
 */
object Utils {

  def printUrl(context: WowcherContext, updateWith: Map[String, String]): String = {
    val urlString = context.request.path
    val params = context.request.queryString.mapValues(_.head) ++ updateWith
    val paramsStr = params.mapValues(URLEncoder.encode(_, "UTF-8")).filterNot(_ == ("page", "1")).map {
      case (k, v) => s"$k=$v"
    }.mkString("&")

    urlString + (if ( paramsStr.nonEmpty ) { s"?$paramsStr" } else "")
  }

  def priceSize(size:Int) = {
    size match {
      case 3 => "char-3"
      case 4 => "char-4"
      case _ => ""
    }
  }
}
