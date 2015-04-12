package backend;

import model.Deal;
import model.DealsListing;
import model.ListDealsOptions;
import model.Location;

import java.util.Arrays;
import java.util.List;

/** I have bodged a "search" adapter here.
 * In Three-Scala, getDeals returns a Future[DealsListing]
 *
 * Here, I am instead retrieving a list of simple deals.
 */
public class SearchAdapter {

    static Location london = new Location("london", "London");
    static Location oxford = new Location("oxford", "Oxford");
    static Location birmingham = new Location("birmingham", "Birmingham");

    static Deal deal1 = new Deal();
    static Deal deal2 = new Deal();
    static Deal deal3 = new Deal();

    public static DealsListing getDeals() {
        List<Deal> deal_list = Arrays.asList(new Deal[]{deal1, deal2, deal3});
        ListDealsOptions list_deals_options = new ListDealsOptions();
        DealsListing deals_listing = new DealsListing();
        deals_listing.deals = deal_list;

        return deals_listing;
    }
}