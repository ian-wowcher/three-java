package controllers

import controllers.WowcherController.WowcherContext
import model.{LocationsListing, Location}
import play.api.mvc.{Request, Result, Action, AnyContent}

import scala.concurrent.{ExecutionContext}

class IanTestFile {




  def createWowcherContext(request: Request[AnyContent]) = {

    WowcherContext(request, locationsCache.get.locations.sortBy(_.name), frontendResourcePath = frontendResourcePath)
  }



}

object adapter {
  def getDeals = {}
}

object locationsCache {

  val locations : Array[Location]= Array{
    Location("london", "London"),
    Location("oxford", "Oxford"),
    Location("birmingham", "Birmingham")};


}
