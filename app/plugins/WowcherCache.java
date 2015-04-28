package plugins;

import backend.ApiAdapter;
import model.DealsListing;
import model.Location;
import play.Plugin;
import play.cache.Cache;
import play.libs.F;
import play.libs.ws.WS;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
import java.util.function.Function;

//TODO implement as a plug-in

/**
 *  Used to build the Wowcher Context
 *
 */
public class WowcherCache extends Plugin {

    public static List<Location> locations() throws Exception {

        List<Location> result = Cache.getOrElse(
                "locations",
                ApiAdapter::getLocations,
                (int) TimeUnit.DAYS.toSeconds(1))
                .get(5, TimeUnit.SECONDS);
        return result;
    }

    public static DealsListing dealListings(String locationId) throws Exception {

        Callable<F.Promise<DealsListing>> callable = makeCallable(locationId, ApiAdapter::getDealsListing);

        DealsListing result = Cache.getOrElse(
                "deals." + locationId,
                callable,
                (int) TimeUnit.DAYS.toSeconds(1))
                .get(5, TimeUnit.SECONDS);
        return result;
    }

    public static Callable makeCallable(String stringArg, Function<String, F.Promise> stringToPromise) {

        return () -> stringToPromise.apply(stringArg);

    }
}