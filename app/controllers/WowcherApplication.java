package controllers;

import backend.ApiAdapter;
import backend.BodgedSearchAdapter;
import model.DealsListing;
import play.libs.F;
import play.mvc.Controller;
import play.mvc.Result;
import util.WowcherContext;

import java.util.function.Function;

public class WowcherApplication extends Controller {

/*
     Prototype version - TODO return a promise result - the framework redeems the promise
*/
    public static F.Promise<Result> apiLocationDeals(String locationId) {
        Function<WowcherContext, F.Promise<Result>> view_generation_code =
                (wowcherContext) -> {
                    F.Promise<DealsListing> dealsListingPromise = ApiAdapter.getDeals(locationId);
                    return dealsListingPromise.map((dlr) -> ok(views.html.dealsPage.apply(dlr, wowcherContext)));
                };
        WowcherContext context_with_location = WowcherContext.createWowcherLocationContext(locationId);
        return WowcherControllerUtil.WowcherAction(context_with_location, view_generation_code);
    }


    /*
    Bodged version
*/
    public static F.Promise<Result> locationDeals(String locationId) {
        Function<WowcherContext, Result> view_generation_code =
                (wowcherContext) -> {
                    DealsListing dealsListing = BodgedSearchAdapter.getDeals();
                    return ok(views.html.dealsPage.apply(dealsListing, wowcherContext));
                };
        WowcherContext context_with_location = WowcherContext.createWowcherLocationContext(locationId);
        return WowcherControllerUtil.BodgedWowcherAction(context_with_location, view_generation_code);
    }


}