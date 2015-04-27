package interceptors;

import play.libs.F;
import play.mvc.Http;
import play.mvc.Result;
import util.WowcherContext;
import play.Logger;

/* This class will intercept every request, add the wowcher context to it, before proceeding with the intended Action*/
public class WowcherInterceptor extends play.mvc.Action.Simple{

    @Override
    public F.Promise<Result> call(Http.Context ctx) throws Throwable {
        final WowcherContext wowcherContext = WowcherContext.createFromHttpContext(ctx);
        ctx.args.put("wowcherContext", wowcherContext);
        Logger.info("Added the WowcherContext to the Http.Context: " + wowcherContext);
        return delegate.call(ctx);
    }


}
