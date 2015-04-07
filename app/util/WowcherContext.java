package util;

import model.Location;
import java.util.List;

import play.mvc.Http.Request;


public class WowcherContext {

    public final List<Location> locationList;
    public final Request request;
    public final List<Location> locations;;
    public final String location;
    public final String frontendResourcePath;

    public WowcherContext(List<Location> locationList, Request request, List<Location> locations, String location, String frontendResourcePath) {
        this.locationList = locationList;
        this.request = request;
        this.locations = locations;
        this.location = null != location ? location : "London" ;
        this.frontendResourcePath = frontendResourcePath;
    }

    public WowcherContext(List<Location> locationList, Request request, List<Location> locations, String frontendResourcePath) {
        this.locationList = locationList;
        this.request = request;
        this.locations = locations;
        this.location = "London" ;
        this.frontendResourcePath = frontendResourcePath;
    }

    public static WowcherContext createWowcherContext(Request request) {
        return new WowcherContext(request)


    }
}