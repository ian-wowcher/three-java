package controllers

import model._
import play.api.mvc._

import scala.concurrent.ExecutionContext

trait WowcherController {
  this: Controller with WowcherSources with WowcherControllerUtil =>

  // todo move this to Nginx
  def indexRedirect = Action {
    implicit request =>
      Redirect(s"/deals/$defaultLocation")
  }

  def status = Action {
    implicit request =>
      Ok("Good")
  }

  // todo move this to Nginx
  def dealRedirect(dealId: Int) = Action {
    implicit request =>
      Redirect(s"/deals/$defaultLocation/$dealId")
  }

  def typeaheadListingToTypeaheadItems(locationId: String)(typeaheadListing: TypeaheadListing): List[TypeaheadItem] = {

    val locations =
      for {
        location <- typeaheadListing.locations
      } yield TypeaheadItem(
        label = location.name,
        category = "Location",
        url = s"/deals/${location.id}/",
        locations = None,
        product = None
      )

    val tags =
      for { tag <- typeaheadListing.tags }
      yield TypeaheadItem(
        label = tag.id,
        category = "Tag",
        url = s"/deals/$locationId/${tag.id}/",
        locations = None,
        product = None
      )

    val categories =
      for { category <- typeaheadListing.categories }
      yield TypeaheadItem(
        label = category.name,
        category = "Category",
        url = s"/deals/$locationId/${category.id}/",
        locations = None,
        product = None
      )

    val deals =
      for { deal <- typeaheadListing.deals }
      yield TypeaheadItem(
        //label = s"${deal.title}",
        label = "",
        category = "Deal",
        url = s"/deals/$locationId/${deal.id}/",
        locations = None,
        product = Option(deal.product)
      )

    locations ++ tags ++ categories ++ deals

  }

  def typeahead(locationId: String, term: String) = WowcherAction {
    implicit wowcherContext => implicit executionContext =>
      adapter.typeahead(term)
        .map(typeaheadListingToTypeaheadItems(wowcherContext.location))
        .map(x => OkAsJson(x))
  }

  def deal(locationId: String, dealId: Int) =
    WowcherLocationAction(locationId) {
      implicit wowContext => implicit executionContext =>
        val currentLocationDealsF = adapter.getDeals(DealsFilter(locationId = Option(locationId)))
        val currentDealF = adapter.getDeal(dealId)
        for {
          dealListing <- currentDealF
          dealsListing <- currentLocationDealsF
        } yield Ok(views.html.deal(dealListing, dealsListing))
    }

  def locationDeals(locationId: String) : Action[AnyContent] =
    WowcherLocationAction(locationId) {
      implicit wowContext => (implicit executionContext =>
      for {
        dealsListing <- adapter.getDeals(
          DealsFilter(
            locationId = Option(locationId),
            pageNo = pageNumber
          )
        )
      }
      yield Ok(views.html.dealsPage(dealsListing))
      )
    }

  def locationCategoryDeals(locationId: String, categoryId: String) : Action[AnyContent] =
    WowcherLocationAction(locationId) {
      implicit wowContext => implicit executionContext =>
      for {
        dealsListing <- adapter.getDeals(
          DealsFilter(
            locationId = Option(locationId),
            categoryId = Option(categoryId),
            pageNo = pageNumber
          )
        )
      } yield Ok(views.html.dealsPage(dealsListing))
    }

  def callStaticPage(locationId: String, pageName: String) =
    WowcherLocationAction(locationId) {
      implicit wowContext => implicit executionContext =>
      adapter.getStaticPage(pageName).map(staticPage => Ok(views.html.pages.staticPage(staticPage)))
    }

  def filterDeals(start: DealsFilter)(fs: (PartialFunction[DealsFilter, DealsFilter])*): DealsFilter = {
    fs.foldLeft(start)((filter, f) => f.lift.apply(filter).getOrElse(filter))
  }

  // todo how do we deal with location?
  def dealsSearch(locationId: String, q: String) =
    WowcherLocationAction(locationId) {
      implicit wowContext => implicit executionContext =>
        for {
          dealsListing <- adapter.getDeals(
            filterDeals(DealsFilter(
              pageNo = pageNumber,
              locationId = Option(locationId)
            ))(
              {
                case filter if q.nonEmpty =>
                  filter.copy(q = Option(q))
              },
              {
                case filter if outCodes.isDefinedAt(PostCodeFragment(q)) =>
                  val PostCodeGeo(postCode, GeoCoordinates(lat, lon)) = outCodes(PostCodeFragment(q))
                  filter.copy(
                    q = None,
                    latLon = Option(lat -> lon),
                    locationId = None,
                    distance = Option(15)
                  )
              }
            )
          )
        }
        yield Ok(views.html.dealsPage(dealsListing))
    }
}
object WowcherController {

  case class WowcherContext
  (request: Request[AnyContent], locations: List[Location] = List.empty, location: String = "london", frontendResourcePath: String
    )
  (implicit val executionContext: ExecutionContext =
  play.api.libs.concurrent.Execution.Implicits.defaultContext)

}