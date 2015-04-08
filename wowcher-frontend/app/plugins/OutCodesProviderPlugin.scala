package plugins

import play.api.{Application, Plugin}
import wowcher.backend.OutCodesProvider
import wowcher.backend.OutCodesProvider._

/**
 * Created on 22/08/2014.
 */
class OutCodesProviderPlugin(implicit app: Application) extends Plugin {
  lazy val outCodes: OutCodesProvider =
    OutCodesProvider.resourceCsvPostCodesProvider
}
