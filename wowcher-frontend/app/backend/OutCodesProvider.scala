package wowcher.backend

import java.io.{Closeable, InputStreamReader}

import com.github.tototoshi.csv.CSVReader
import generic.Util
import wowcher.backend.OutCodesProvider.{OutCodesProvider, PostCodeGeo, GeoCoordinates}
import play.api.{Application, Play}

/**
 * Created on 04/08/2014.
 */

object OutCodesProvider {
  type OutCodesProvider = PartialFunction[PostCodeFragment, PostCodeGeo]
  case class PostCodeFragment(value: String) extends AnyVal
  case class GeoCoordinates(lat: String, lon: String)
  case class PostCodeGeo(outCode: String, geo: GeoCoordinates)

  def resourceCsvPostCodesProvider(implicit app: Application): OutCodesProvider = {

    val database: Vector[PostCodeGeo] = {
      Util.using(Play.classloader.getResourceAsStream("resources/postcodes.csv")) { inputStream =>
        Util.using(new InputStreamReader(inputStream)) { reader =>
          Util.using(CSVReader.open(reader)) { csvReader =>
            csvReader.all() match {
              case List("id", "outcode", "lat", "lng") :: data =>
                for {
                  _ :: outcode :: lat :: lon :: Nil <- data
                } yield PostCodeGeo(outcode.toUpperCase, GeoCoordinates(lat, lon))
            }
          }.toVector
        }
      }
    }

    // postcodes are uppercased
    Function.unlift(q => database.find(q.value.toUpperCase startsWith _.outCode))

  }
}

