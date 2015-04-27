package controllers;

import backend.ApiAdapter;
import backend.BodgedSearchAdapter;
import interceptors.WowcherInterceptor;
import model.DealsListing;
import play.libs.F;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
import util.WowcherContext;

import java.util.function.Function;

public class WowcherApplication extends Controller {

/*
     1) Uses the Api to get a deals listing
     Generates the dealsPage
     All async by only returning promises.
*/
    @With(WowcherInterceptor.class)
    public static F.Promise<Result> locationDeals(String locationId) {
        Function<WowcherContext, F.Promise<Result>> view_generation_code =
                (wowcherContext) -> {
                    F.Promise<DealsListing> dealsListingPromise = ApiAdapter.getDeals(locationId);
                    return dealsListingPromise.map((dlr) -> ok(views.html.dealsPage.apply(dlr)));
                };
        WowcherContext wowcherContext = (WowcherContext) ctx().args.get("wowcherContext");
        WowcherContext context_with_location = WowcherContext.replaceLocationInContext(locationId, wowcherContext);
        ctx().args.put("wowcherContext", context_with_location);
        return WowcherControllerUtil.WowcherAction(context_with_location, view_generation_code);
    }
}