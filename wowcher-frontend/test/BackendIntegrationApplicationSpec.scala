import com.typesafe.config.ConfigFactory
import org.scalatest._
import play.api.test._
import play.api.test.Helpers._
import org.scalatestplus.play._
import scala.util.{Success, Failure, Try}

object RequiresBackend extends Tag("wowcher.RequiresBackend")
class BackendIntegrationApplicationSpec extends PlaySpec with OneAppPerSuite with BeforeAndAfterAll {

  def backendPort = {
    // Odd. Activator doesn't pass in -Dtest.backend.port=1234 etc
    val getPortResults = Vector(
      "Get typesafe port" -> Try(ConfigFactory.load().getInt("test.backend.port")),
      "Get environment port" -> Try(System.getenv("TEST_BACKEND_PORT").toInt),
      "Get default port" -> Try(9872)
    )
    for {
      (what, Failure(f)) <- getPortResults.filter(_._2.isFailure)
    } { println(s"Failed to $what due to $f") }

    (for {
      (what, Success(port)) <- getPortResults.find(_._2.isSuccess)
    } yield {
      println(s"Got port $port from: $what")
      port
    }).get
  }

  def backendUrl =
    s"http://127.0.0.1:$backendPort"

  implicit override lazy val app = {
    FakeApplication(
      additionalConfiguration =
        Map("backendUrl" -> backendUrl)
    )
  }

  // todo write a stub backend for this.
  // todo have integration tests elsewhere - this is the wrong place for these.
  "Application" should {
    "render the deals page" taggedAs (RequiresBackend) in {
      val dealsPage = route(FakeRequest(GET, "/deals")).get
      status(dealsPage) mustBe OK
      contentAsString(dealsPage) must include ("Bridal Makeup Course")
    }

    "render the 'London' deals page" taggedAs (RequiresBackend) in {
      val londonDeals = route(FakeRequest(GET, "/deals/london")).get
      contentType(londonDeals).get mustBe "text/html"
      contentAsString(londonDeals) must include ("Helicopter Flying Experience")
    }

    "render autocomplete for 'Luxury' in London" taggedAs (RequiresBackend) in {
      val londonDeals = route(FakeRequest(GET, "/autocomplete?locationId=london&q=luxury")).get
      contentType(londonDeals).get mustBe "text/json"
      contentAsString(londonDeals) must include ("Luxury Podiatry Treatment")
    }

    "render autocomplete for 'London'" taggedAs (RequiresBackend) in {
      val londonDeals = route(FakeRequest(GET, "/autocomplete?locationId=london&q=london")).get
      contentType(londonDeals).get mustBe "text/json"
      contentAsString(londonDeals) must include ("london-south")
    }

    "render a deal page" taggedAs (RequiresBackend) in {
      val dealPage = route(FakeRequest(GET, "/deals/london/196468")).get
      contentAsString(dealPage) must include ("Bridal Makeup Course")
      contentAsString(dealPage) must include ("£199")
    }

    "render London electronics" taggedAs (RequiresBackend) in {
      val londonElectronicsDeals = route(FakeRequest(GET, "/deals/london/electronics")).get
      contentAsString(londonElectronicsDeals) must include ("Android Dual Core Tablet")
      contentAsString(londonElectronicsDeals) must include ("£46.98 (from D2D)")
    }

    "render Plymouth deals with Plymouth as the main item" taggedAs (RequiresBackend) in {
      val plymouthDeals = route(FakeRequest(GET, "/deals/plymouth")).get
      contentAsString(plymouthDeals) must include ("With rubber armrests")
      contentAsString(plymouthDeals) must include ("""value="plymouth" selected="selected">""")
    }

  }

}
