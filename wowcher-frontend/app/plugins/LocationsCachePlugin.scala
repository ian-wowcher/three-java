package plugins

import akka.actor.Kill
import akka.util.Timeout
import generic.DataCaching
import play.api.{Application, Play, Plugin}

import scala.concurrent.Await

/**
 * Created on 22/08/2014.
 */
class LocationsCachePlugin(implicit app: Application) extends Plugin {

  def adapter = Play.application.plugin[SearchAdapterPlugin].get.adapter

  lazy val mainActor = {
    import scala.concurrent.duration._
    val cacheOptions = CacheOptions(
      timeToLive = 10.minutes,
      failedRetryInterval = 5.seconds
    )(() => adapter.getLocations)
    val props = DataCaching.props[LocationsListing](cacheOptions)
    play.api.libs.concurrent.Akka.system.actorOf(props)
  }

  lazy val locationsCache = new {
    import akka.pattern.ask

    import scala.concurrent.duration._
    implicit val timeout = Timeout(5.seconds)
    def get : LocationsListing = Await.result(mainActor.ask(DataCaching.Get).mapTo[LocationsListing], 5.seconds)
  }

  override def enabled = true

  override def onStop(): Unit = {
    mainActor ! Kill
  }

}
