package controllers

import model.Location
import play.api.mvc.{AnyContent, Request}

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
