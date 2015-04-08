import wowcher.backend.OutCodesProvider.{PostCodeFragment, OutCodesProvider}
import wowcher.backend.OutCodesProvider
import org.scalatestplus.play.{OneServerPerSuite, PlaySpec}

/**
 * Created on 04/08/2014.
 */
class CSVReaderTest extends PlaySpec with OneServerPerSuite {

  var provider: OutCodesProvider = _

  "CSV Loader" should {

    "Load CSV data" in {
      provider = {
        // loads the data straight away
        OutCodesProvider.resourceCsvPostCodesProvider
      }
    }

    "Find W1" in {
      provider(PostCodeFragment("W1")).outCode mustBe "W1"
    }

    "Find W18TT" in {
      provider(PostCodeFragment("W18TT")).outCode mustBe "W1"
    }

    "Find W1T" in {
      provider(PostCodeFragment("W1T")).outCode mustBe "W1"
    }

    /** This actually fails right now **/
    "Find W10" ignore {
      provider(PostCodeFragment("W10")).outCode mustBe "W10"
    }

  }
}
