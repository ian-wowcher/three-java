package controllers

import play.api.Play
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try

/**
 * Created on 01/08/2014.
 */
trait WowcherControllerUtil {
  this: Controller with WowcherSources =>

  def cookieLocation(implicit request: Request[_]) =
    request.cookies.get("chosen-home").map(_.value)

  def defaultLocation(implicit request: Request[_]) =
    cookieLocation.getOrElse("london")

  lazy val frontendResourcePath =
    Play.current.configuration.getString("frontendResourcePath").get

  def createWowcherContext(request: Request[AnyContent]) = {

    WowcherContext(request, locationsCache.get.locations.sortBy(_.name), frontendResourcePath = frontendResourcePath)
  }

  def ActionWithContext(f: WowcherContext => Result) = {
    Action { request =>
      f(createWowcherContext(request))
    }
  }

  def WowcherAction(passedInFunction: WowcherContext => ExecutionContext => Future[Result]) : Action[AnyContent] = {
    Action.async { implicit request =>
      val wowcherContext = createWowcherContext(request)
      passedInFunction(wowcherContext)(wowcherContext.executionContext)
    }
  }

  def WowcherLocationAction(locationId: String)(f: WowcherContext => ExecutionContext => Future[Result]) : Action[AnyContent] =
    WowcherAction(implicit c => implicit ec => f(c.copy(location = locationId))(ec))


  def OkAsJson[T<:AnyRef](obj: T)(implicit mf: Manifest[T]) = {
    Ok(JsonUtility.printPretty(obj)).as("text/json")
  }

  //remove trailing slashes and redirect accordingly
  def untrail(path: String) = Action {
    MovedPermanently("/" + path)
  }

  def pageNumber(implicit wowContext: WowcherContext) =
    Try(wowContext.request.queryString("page").head.toInt).getOrElse(1)


  /* Slug the part of an URL which identifies a page using human-readable keywords*/
  def slugify(str: String): String = {
    import java.text.Normalizer
    Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\w ]", "").replace(" ", "-").toLowerCase
  }
}
