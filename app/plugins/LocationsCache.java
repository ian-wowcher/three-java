package plugins;

import model.Location;
import play.Plugin;

import java.util.Arrays;
import java.util.List;

//TODO implement as a plug-in

/**
 *  Used to build the Wowcher Context
 *
 */
public class LocationsCache extends Plugin {

        //this will eventually cache the deals API response
        static Location[] locations = {
                    new Location("london", "London"),
                    new Location("oxford", "Oxford"),
                    new Location("birmingham", "Birmingham")
        };


        public static List<Location> locations() {
            return Arrays.asList(locations);
        }

//        def get_locations: List[Location] = locations.asInstanceOf[List[Location]]

    }