package model


/**
 * Created on 22/08/2014.
 */

case class TypeaheadItem(label: String, category: String, url: String, locations: Option[List[Location]], product: Option[String])
