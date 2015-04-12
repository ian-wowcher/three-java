package plugins

import play.api.{Application, Plugin}

/**
 * Created on 22/08/2014.
 */
class OutCodesProviderPlugin(implicit app: Application) extends Plugin {
  lazy val outCodes: OutCodesProvider =
    OutCodesProvider.resourceCsvPostCodesProvider
}
