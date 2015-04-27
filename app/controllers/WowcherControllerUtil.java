package controllers;

import play.libs.F;
import play.mvc.Controller;
import play.mvc.Result;
import util.WowcherContext;

import java.util.function.Function;

public class WowcherControllerUtil extends Controller {

    /**
     * The last method in request handling which executes all action methods.
     *
     * This can be extended to manipulate the WowcherContext.
     * */
    public static F.Promise<Result> WowcherAction(WowcherContext wowcherContext,
                                                  Function<WowcherContext, F.Promise<Result>> view_generation_code){
        //invoke the passed in function
        return view_generation_code.apply(wowcherContext);
    }
}