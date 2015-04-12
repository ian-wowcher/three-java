package controllers;

import backend.SearchAdapter;
import model.DealsListing;
import play.libs.F;
import play.mvc.Controller;
import play.mvc.Result;
import util.WowcherContext;

import java.util.function.Function;


public class WowcherApplication extends Controller {

    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }

    public static F.Promise<Result> locationDeals(String locationId) {
        Function<WowcherContext, Result> view_generation_code =
                (wowcherContext) -> {
                DealsListing dealsListing = SearchAdapter.getDeals();
                return ok(views.html.dealsPage(dealsListing, wowcherContext));
        };
        WowcherContext context_with_location = WowcherContext.createWowcherLocationContext(locationId);
        return WowcherControllerUtil.WowcherAction(context_with_location, view_generation_code);
    }
}