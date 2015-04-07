package controllers;

import play.*;
import play.mvc.*;

import util.WowcherContext;
import views.html.*;

public class WowcherApplication extends Controller {

    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }

    public static Result locationDeals(String locationId) {

        WowcherContext wowcherContext = WowcherContext.getFromRequest(request());


        return ok(views.html.dealsPage(dealsListing));
    }
}