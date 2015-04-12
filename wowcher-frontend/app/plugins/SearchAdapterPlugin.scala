package plugins

import play.api.{Application, Play, Plugin}

/**
 * Created on 01/08/2014.
 */

class SearchAdapterPlugin(implicit app: Application) extends Plugin {

  override def enabled = true

  lazy val adapter: SearchAdapter = {
    new SearchAdapterImpl(
      Play.application.configuration.getString("backendUrl")
        .orElse(Play.application.configuration.getString("searchUrl"))
        .getOrElse(throw new IllegalArgumentException("backendUrl/searchUrl missing"))
    )
  }

}
