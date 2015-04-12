package util;

import model.Location;
import play.mvc.Http.Request;
import plugins.LocationsCache;

import java.util.List;


public final class WowcherContext {

    public final Request request;
    public final List<Location> locations;;
    public final String location;
    public final String frontendResourcePath;

    public WowcherContext(Request request, List<Location> locations, String location, String frontendResourcePath) {
        this.request = request;
        this.locations = locations;
        this.location = location;
        this.frontendResourcePath = frontendResourcePath;
    }

    public WowcherContext(WowcherContext wowcherContext, Request request) {
        this(
                request,
                wowcherContext.locations,
                wowcherContext.location,
                wowcherContext.frontendResourcePath);
    }

    public static WowcherContext createWowcherLocationContext(String locationId) {
        /*
        *  def createWowcherContext(request: Request[AnyContent]) = {

    WowcherContext(request, locationsCache.get.locations.sortBy(_.name), frontendResourcePath = frontendResourcePath)
  }
        * */
        return new WowcherContext(
                null,
                LocationsCache.locations(),
                locationId,
                "/frontend_resource_path" //TODO get from application configuration
        );
    }

    public static WowcherContext createWowcherContextWithRequest(WowcherContext wowcherContext, Request request) {
        return new WowcherContext(
                request,
                wowcherContext.locations,
                wowcherContext.location,
                wowcherContext.frontendResourcePath
        );
    }
}