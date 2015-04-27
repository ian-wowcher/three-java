package model;

import java.util.List;

/**
 * TODO make immutable
 * */
public class DealsListing {
    public ListDealsOptions filter;
    public int total;
    public int totalPages;
    public final List<Deal> deals;

    public DealsListing(List<Deal> deals, int totalPages, int total, ListDealsOptions filter) {
        this.deals = deals;
        this.totalPages = totalPages;
        this.total = total;
        this.filter = filter;
    }

    public DealsListing(List<Deal> deals) {
        this.deals = deals;
    }
}
