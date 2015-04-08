package controllers

import play.api.mvc._

object WowcherApplication extends Controller with WowcherController with WowcherSources with WowcherControllerUtil

