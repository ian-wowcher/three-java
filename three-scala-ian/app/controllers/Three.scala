package controllers

import model.{DealsListing, Location}
import play.api.mvc._
import scala.concurrent.{Future, ExecutionContext}


object Three extends Controller {

  lazy val frontendResourcePath = "/build" //TODO hard coded but normally gotten from the application configuration.

  def createWowcherContext(request: Request[AnyContent]) = {
      WowcherContext(request, locationsCache.get_locations.sortBy(_.name), frontendResourcePath = frontendResourcePath)
    }

  def locationDeals(locationId: String) : Action[AnyContent] =
    WowcherLocationAction(locationId) {
      implicit wowContext => (implicit executionContext =>
        for {
          dealsListing <- search_adapter.getDeals( // this needs tor
            //DealsFilter(locationId = Option(locationId),pageNo = pageNumber)
          )
        }
        yield Ok(views.html.dealsPage(dealsListing))
        )
    }

    def WowcherLocationAction(locationId: String)(f: WowcherContext => ExecutionContext => Future[Result]) : Action[AnyContent] =
      WowcherAction(implicit c => implicit ec => f(c.copy(location = locationId))(ec))


    def WowcherAction(passedInFunction: WowcherContext => ExecutionContext => Future[Result]) : Action[AnyContent] = {
      Action.async { implicit request =>
        val wowcherContext = createWowcherContext(request)
        passedInFunction(wowcherContext)(wowcherContext.executionContext)
      }
    }

  }




/* Used to build the Wowcher Context*/
  object locationsCache {

    //this will eventually cache the deals API response
    val locations : Array[Location]= Array{
      Location("london", "London"),
      Location("oxford", "Oxford"),
      Location("birmingham", "Birmingham")
    };

    def get_locations : List[Location] = locations.asInstanceOf[List[Location]]
  }

  case class WowcherContext
  (request: Request[AnyContent], locations: List[Location] = List.empty, location: String = "london", frontendResourcePath: String)
  (implicit val executionContext: ExecutionContext = play.api.libs.concurrent.Execution.Implicits.defaultContext)






}