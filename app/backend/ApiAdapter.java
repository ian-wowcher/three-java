package backend;

import com.fasterxml.jackson.databind.JsonNode;
import model.Deal;
import model.DealsListing;
import model.ListDealsOptions;
import model.Location;
import play.libs.F;
import play.libs.ws.WS;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** TODO Use the existing Wowcher API to .
 * In Three-Scala, getDeals returns a Future[DealsListing]
 *
 * Here, I am instead retrieving a list of simple deals.
 */
public class ApiAdapter {

    private static final String LOCATION_PLACEHOLDER = "{location}";
    private static final String DEALS_BY_LOCATION = "http://api.wowcher.co.uk/deals/" + LOCATION_PLACEHOLDER;

    static Location london = new Location("london", "London");
    static Location oxford = new Location("oxford", "Oxford");
    static Location birmingham = new Location("birmingham", "Birmingham");

    public static F.Promise<DealsListing> getDeals(String location) {

        final String feedUrl = DEALS_BY_LOCATION.replace(LOCATION_PLACEHOLDER, location);
        return WS.url(feedUrl).setHeader("Accept", "application/json").get().map(
                response -> {
                    System.out.println("the beginning of the response is : " + response.getBody().substring(0, 100));

                    JsonNode dealFeedResponse = response.asJson();
                    List<Deal> dealList = new ArrayList();

                    JsonNode deals = dealFeedResponse.findPath("theDeals");
                    deals.iterator().forEachRemaining(
                            node -> {
                                String title = node.findValue("title").textValue();
                                String dealDescription = node.findValue("dealDescription").textValue();
                                String businessName = node.findValue("businessName").textValue();
                                dealList.add(new Deal(title, dealDescription, businessName));
                            }
                    );
                    return new DealsListing(dealList);
                }
        );
    }
}