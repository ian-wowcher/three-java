package plugins;

import play.Plugin;
import play.Application;
import akka.actor.ActorRef;



/**
 * Created by ian.polding on 04/03/2015.
 */
public class LocationsCachePlugin extends Plugin {

    private final Object myComponent = new Object();

    public void onStart() {
    }

    /**
     * Called when the application stops.
     */
    public void onStop() {
    }

    /**
     * Is this plugin enabled.
     */
    public boolean enabled() {
        return true;
    }

}
