package util;

import model.Location;
import play.mvc.Http;
import play.mvc.Http.Request;
import plugins.WowcherCache;

import java.util.List;


public final class WowcherContext {

    public final Request request;
    public final List<Location> locations;;
    public final String location;
    public final String frontendResourcePath;

    public static final String defaultLocation = "London";

    public WowcherContext(Request request, List<Location> locations, String location, String frontendResourcePath) {
        this.request = request;
        this.locations = locations;
        this.location = location;
        this.frontendResourcePath = frontendResourcePath;
    }

    public static WowcherContext createFromHttpContext(Http.Context httpContext) throws Exception {

        List<Location> cachedLocations = WowcherCache.locations();
        String frontendResourcePath = "frontendResourcePath";

        return new WowcherContext(
                httpContext.request(),
                cachedLocations,
                defaultLocation,
                frontendResourcePath
        );

    }

    public static WowcherContext replaceLocationInContext(String locationId, WowcherContext wowcherContext) {

        return new WowcherContext(
                wowcherContext.request,
                wowcherContext.locations,
                locationId,
                wowcherContext.frontendResourcePath
        );
    }

//TODO now redundant
//    public static WowcherContext recreateWowcherContextWithRequest(WowcherContext wowcherContext, Request request) {
//        return new WowcherContext(
//                request,
//                wowcherContext.locations,
//                wowcherContext.location,
//                wowcherContext.frontendResourcePath
//        );
//    }





    @Override
    public String toString() {
        return "WowcherContext{" +
                "request=" + request + "\n" +
                ", locations=" + locations + "\n" +
                ", location='" + location + '\'' + "\n" +
                ", frontendResourcePath='" + frontendResourcePath + '\'' +
                '}';
    }
}