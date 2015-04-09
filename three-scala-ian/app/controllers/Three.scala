package controllers

import backend.SearchAdapter
import model.{Location, SimpleDeal}
import play.api.mvc.{Controller, Request, AnyContent, Action, Result}
import scala.concurrent.{ExecutionContext, Future}

object Three extends Controller {

  lazy val frontendResourcePath = "/build" //TODO hard coded but normally gotten from the application configuration.
  val search_adapter = new SearchAdapter


  def createWowcherContext(request: Request[AnyContent]) = {
    WowcherContext(request, locationsCache.get_locations.sortBy(_.name), frontendResourcePath = frontendResourcePath)
  }

  def locationDeals(locationId: String) : Action[AnyContent] =
    WowcherLocationAction(locationId) {
      implicit wowContext => (implicit executionContext =>
        for {
          dealsListing <- search_adapter.getDeals()
        }
          yield Ok(views.html.dealsPage(dealsListing))
        )
    }

  def WowcherLocationAction(locationId: String)(function_for_wowcher_action: WowcherContext => ExecutionContext => Future[Result]) : Action[AnyContent] =
    WowcherAction(implicit c => implicit ec => function_for_wowcher_action(c.copy(location = locationId))(ec))

  def WowcherAction(function_to_excecute: WowcherContext => ExecutionContext => Future[Result]) : Action[AnyContent] = {
    Action.async {
      implicit request =>
      val wowcherContext : WowcherContext = createWowcherContext(request)
      function_to_excecute(wowcherContext)(wowcherContext.executionContext)
    }
  }

  def expanded_locationDeals(locationId: String) : Action[AnyContent] =
    WowcherLocationAction(locationId) {
      implicit wowContext => (implicit executionContext =>
        for {
          dealsListing <- search_adapter.getDeals()
        }
          yield Ok(views.html.dealsPage(dealsListing))
        )
    }


}




/* Used to build the Wowcher Context*/
object locationsCache {

  //this will eventually cache the deals API response
  val locations: Array[Location] = Array {
    Location("london", "London"),
    Location("oxford", "Oxford"),
    Location("birmingham", "Birmingham")
  }


  def get_locations: List[Location] = locations.asInstanceOf[List[Location]]

}

case class WowcherContext(request: Request[AnyContent], locations: List[Location] = List.empty, location: String = "london", frontendResourcePath: String)
                           (implicit val executionContext: ExecutionContext = play.api.libs.concurrent.Execution.Implicits.defaultContext)






}