package model

/**
 * Created on 22/08/2014.
 */
case class DealsFilter(
                           q: Option[String] = None,
                           locationId: Option[String] = None,
                           categoryId: Option[String] = None,
                           pageNo: Int = 1,
                           latLon: Option[(String, String)] = None,
                           distance: Option[Int] = None,
                           pageSize: Int = 10
                           )
