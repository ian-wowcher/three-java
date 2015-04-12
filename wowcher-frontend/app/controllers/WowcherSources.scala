package controllers

import play.api.Play
import play.api.mvc._


trait WowcherSources {
  this: Controller =>

  import Play.current
  def locationsCache = Play.application.plugin[LocationsCachePlugin].get.locationsCache
  def adapter = Play.application.plugin[SearchAdapterPlugin].get.adapter
  def outCodes = Play.application.plugin[OutCodesProviderPlugin].get.outCodes
}



