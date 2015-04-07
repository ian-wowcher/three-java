package model;

import java.util.List;

public class Deal {

    public final Long id;
    public final String title;
    public final String product;
    public final List<Location> locations;

    public Deal(List<Location> locations, String product, String title, Long id) {
        this.locations = locations;
        this.product = product;
        this.title = title;
        this.id = id;
    }
}
