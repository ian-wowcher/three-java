package controllers;

import play.libs.F;
import play.mvc.Controller;
import play.mvc.Result;
import util.WowcherContext;

import java.util.function.Function;

public class WowcherControllerUtil extends Controller {

/*
* def WowcherLocationAction(passed_in_locationId: String)(passed_in_func: WowcherContext => (ExecutionContext => Future[Result])) =
    WowcherAction(implicit c => implicit ec => passed_in_func(c.copy(location = passed_in_locationId))(ec))
*
* */
    /*public static Function<Http.Request, Result> WowcherLocationAction(String passed_in_locationId,
                                                                       Function<WowcherContext, Function<WowcherContext,
                                                                               Integer>> passed_in_func) {
        // give the Wowcher context to the passed in function
        WowcherContext wowcherContext = WowcherContext.createWowcherLocationContext(passed_in_locationId);
        return WowcherAction(passed_in_func.apply(wowcherContext));
    }*/

    /*
    * WowcherAction(f: WowcherContext => ExecutionContext => Future[Result])
    * returns: (an async action!!) Action[AnyContent]
    * */
    public static F.Promise<Result> WowcherAction(WowcherContext wowcherContext,
                                                               Function<WowcherContext, Result> view_generation_code){
        //add request information to the context
        WowcherContext createdWowcherContext = WowcherContext.recreateWowcherContextWithRequest(wowcherContext, request());
        //invoke the passed in function
        return F.Promise.promise(() -> view_generation_code.apply(createdWowcherContext));
    }

    private static WowcherContext createWowcherContext() {
        //TODO static factory method of the WowcherContext class?
        return null;
    }
}