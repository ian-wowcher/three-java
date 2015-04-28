package backend;

import com.fasterxml.jackson.databind.JsonNode;
import model.Deal;
import model.DealsListing;
import model.ListDealsOptions;
import model.Location;
import play.api.libs.concurrent.Promise;
import play.cache.Cache;
import play.libs.F;
import play.libs.ws.WS;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** TODO Use the existing Wowcher API to .
 * In Three-Scala, getDealsListing returns a Future[DealsListing]
 *
 * Here, I am instead retrieving a list of simple deals.
 */
public class ApiAdapter {

    private static final String LOCATION_PLACEHOLDER = "{location}";
    private static final String DEALS_BY_LOCATION = "http://api.wowcher.co.uk/deals/" + LOCATION_PLACEHOLDER;
    private static final String LOCATIONS = "http://api.wowcher.co.uk/locations/";

    static Location london = new Location("london", "London");
    static Location oxford = new Location("oxford", "Oxford");

    public static F.Promise<DealsListing> getDealsListing(String location) {

        final String feedUrl = DEALS_BY_LOCATION.replace(LOCATION_PLACEHOLDER, location);
        F.Promise<DealsListing> apiResult =  WS.url(feedUrl).setHeader("Accept", "application/json").get().map(
                response -> {

                    JsonNode dealFeedResponse = response.asJson();
                    List<Deal> dealList = new ArrayList();

                    JsonNode deals = dealFeedResponse.findPath("theDeals");
                    deals.iterator().forEachRemaining(
                            node -> {
                                String title = node.findValue("title").textValue();
                                String dealProduct = node.findValue("dealProduct").textValue();
                                String dealDescription = node.findValue("dealDescription").textValue();
                                String businessName = node.findValue("businessName").textValue();
                                dealList.add(new Deal(dealProduct, title, dealDescription, businessName));
                            }
                    );
                    return new DealsListing(dealList);
                }
        );

        return apiResult;
    }
    public static F.Promise<List<Location>> getLocations() {

        final String feedUrl = LOCATIONS;
        F.Promise<List<Location>> apiResult =  WS.url(feedUrl).setHeader("Accept", "application/json").get().map(
                response -> {
                    System.out.println("Calling locations API");
                    JsonNode dealFeedResponse = response.asJson();
                    List<Location> locationList = new ArrayList();

                    JsonNode deals = dealFeedResponse.findPath("theLocations");
                    deals.iterator().forEachRemaining(
                            node -> {
                                String name = node.findValue("locationName").textValue();
                                String path = node.findValue("locationPath").textValue();
                                locationList.add(new Location(path, name));
                            }
                    );
                    return locationList;
                }
        );
        return apiResult;
    }


}