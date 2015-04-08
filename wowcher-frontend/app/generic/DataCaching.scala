package generic

import akka.actor.{ActorRef, Props, Scheduler}

import scala.concurrent._
import scala.concurrent.duration._
import scala.util.{Failure, Success, Try}

/**
 * Created on 28/07/2014.
 */
object DataCaching {

  case class CacheOptions[+T](timeToLive: FiniteDuration, failedRetryInterval: FiniteDuration)(val f: () => Future[T])
  import akka.actor.ActorDSL._
  case object Refresh
  case object Get

  def props[T](cacheOptions: CacheOptions[T]) = Props(classOf[DataCaching.CachedActor[String]], cacheOptions)

  /** Fulfil the future with value of 'result' after 'delay' **/ 
  def delayedFuture[T](delay: FiniteDuration)(result: => T)(implicit executor: ExecutionContext, scheduler: Scheduler): Future[T] = {
    val promise = Promise[T]()
    scheduler.scheduleOnce(delay) {
      promise.tryComplete(Try(result))
    }
    promise.future
  }

  /** Caches values with preference for having a Success rather than a Failure **/
  class CachedActor[T](cacheOptions: CacheOptions[T]) extends Act {

    import scala.concurrent.ExecutionContext.Implicits.global

    case class ResultReceived(result: Try[T])

    whenStarting {
      become(nonValued(waitingClients = Set.empty))
      self ! Refresh
    }

    import akka.pattern.pipe
    def valued(value: Try[T]): Receive = {
      case Get =>
        Future.fromTry(value) pipeTo sender
      case Refresh =>
        cacheOptions.f().onComplete {
          tryResult =>
            self ! ResultReceived(tryResult)
        }
      case ResultReceived(success @ Success(_)) =>
        become(valued(success))
        context.system.scheduler.scheduleOnce(cacheOptions.timeToLive, self, Refresh)
      case ResultReceived(failure @ Failure(_)) =>
        context.system.scheduler.scheduleOnce(cacheOptions.failedRetryInterval, self, Refresh)
    }

    def nonValued(waitingClients: Set[ActorRef]): Receive = {
      case Get =>
        become(nonValued(waitingClients + sender))
      case Refresh =>
        cacheOptions.f().onComplete(self ! ResultReceived(_))
      case ResultReceived(result) =>
        become(valued(result))
        for {
          waitingClient <- waitingClients
        } Future.fromTry(result) pipeTo waitingClient
        result match {
          case Success(value) =>
            context.system.scheduler.scheduleOnce(cacheOptions.timeToLive, self, Refresh)
          case Failure(reason) =>
            context.system.scheduler.scheduleOnce(cacheOptions.failedRetryInterval, self, Refresh)
        }

    }

  }

}
