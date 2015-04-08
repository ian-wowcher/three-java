package model

/**
 * Created on 22/08/2014.
 */
case class Address(
                       businessName: String,
                       addressLine1: String,
                       addressLine2: Option[String],
                       town: String,
                       postcode: String,
                       latlon: Option[String],
                       latitude: Option[String],
                       longitude: Option[String])
