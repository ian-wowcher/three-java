package model;

import java.util.List;

/**
 * TODO make fully immutable
 * */
public class DealsListing {
    public ListDealsOptions filter;
    public int total;
    public int totalPages;
    public final List<Deal> deals;

    public DealsListing(List<Deal> deals) {
            this.deals = deals;
    }
}
