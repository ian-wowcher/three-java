package backend

import model._
import scala.concurrent.Future

trait SearchAdapter {

  def getDeal(dealId: Int): Future[DealListing]

  def getDeals(filter: DealsFilter): Future[DealsListing]

  def getCategories: Future[CategoriesListing]

  def getTags: Future[TagsListing]

  def getLocations: Future[LocationsListing]

  def typeahead(q: String): Future[TypeaheadListing]

  def autoCompletePassthrough(q: String): Future[String]

  def getStaticPage(pageName:String): Future[StaticPageListing]

}

