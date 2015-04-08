package model

/**
 * Created on 22/08/2014.
 */
case class ListDealsOptions
(page: Int, pageSize: Int, location: Option[String], geo: Option[GeoCords],
    postcode: Option[String], tags: Option[List[String]], sort: String,
    filter: String, focus: Option[String], q: Option[String]
     )
