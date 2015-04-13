package backend

import java.net.URLEncoder

import play.api.libs.ws.WS

import scala.concurrent.ExecutionContext

/**
  * Created on 22/07/2014.
  */
class BodgedSearchAdapterImpl(root: String)(implicit ec: ExecutionContext, app: play.api.Application) extends BodgedSearchAdapter {

   private def getBody(url: String) =
     for {
       response <- WS.url(url).get()
       body = response.body
       _ = if ( response.status != 200 ) {
         throw new NoSuchElementException(s"URL at $url not found, status ${response.status}, body: ${body.take(50)}...")
       }
     }
     yield body

   private def get[T<: AnyRef](url: String)(implicit mf: scala.reflect.Manifest[T]) =
     for { body <- getBody(url) }
     yield {
       JsonUtility.extract[T](body)
     }

   override def typeahead(q: String) = get[TypeaheadListing]{
     val qEncoded = URLEncoder.encode(q, "UTF-8")
     s"$root/typeahead/?q=$qEncoded"
   }

   // TODO remove this if we don't need to perform any transformations on the data.

   override def autoCompletePassthrough(q: String) = getBody {
     val qEncoded = URLEncoder.encode(q, "UTF-8")
     s"$root/autocomplete/?q=$qEncoded"
   }

   override def getDeal(dealId: Int) = get[DealListing](s"$root/deals/$dealId/")

   override def getStaticPage(pageName: String) = get[StaticPageListing](s"$root/pages/$pageName")

   override def getCategories = get[CategoriesListing](s"$root/categories/")

   override def getLocations = get[LocationsListing](s"$root/locations/")

   override def getTags = get[TagsListing](s"$root/tags/")

   override def getDeals(filter: DealsFilter) = {
     get[SimpleDealsListing] {
       val locationParam = locationId.map(URLEncoder.encode(_, "UTF-8")).map(i => s"location=$i").toList
       val qParam = q.map(URLEncoder.encode(_, "UTF-8")).map(i => s"q=$i").toList
       val distanceParam = distance.map(_.toString).map(URLEncoder.encode(_, "UTF-8")).map(i => s"distance=$i").toList
       val latLonParams = latLon.toList.map {
         ll =>
           val latE = URLEncoder.encode(ll._1, "UTF-8")
           val lonE = URLEncoder.encode(ll._2, "UTF-8")
           s"lon=$lonE&lat=$latE"
       }
       val categoryParams = categoryId.map(URLEncoder.encode(_, "UTF-8")).map(i => s"category=$i").toList
       val pageParam = List(s"page=$pageNo")
       val params = List(locationParam, qParam, distanceParam, latLonParams, categoryParams, pageParam).flatten
       val paramsS = params.mkString("&")
       s"$root/deals/?$paramsS"
     }
   }
 }
