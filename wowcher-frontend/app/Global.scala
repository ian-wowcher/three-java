/**
 * Created on 27/08/2014.
 */

import play.api.mvc._
import play.api.{GlobalSettings, Play}
object Global extends GlobalSettings {
  override def doFilter(next: EssentialAction): EssentialAction = {
    Filters(super.doFilter(next), new HTMLCompressorFilter({
      val compressor = new HtmlCompressor()
      compressor.setEnabled(true)
      import play.api.Play.current
      // it does work. Yay.
      if ( Play.isDev ) {
        compressor.setPreserveLineBreaks(true)
      }
      compressor.setSimpleDoctype(true)
      compressor.setRemoveScriptAttributes(true)
      compressor.setRemoveLinkAttributes(true)
      compressor.setRemoveFormAttributes(true)
      compressor.setRemoveHttpProtocol(true)
      compressor.setRemoveHttpsProtocol(true)
      compressor.setRemoveComments(true)
      compressor.setRemoveIntertagSpaces(true)
      compressor.setRemoveMultiSpaces(true)
      compressor
    }))
  }
}
