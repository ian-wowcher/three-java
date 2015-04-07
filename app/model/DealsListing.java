package model;

import java.util.List;

public class DealsListing {

    public final ListDealsOptions filter;
    public final int total;
    public final int totalPages;
    public final List<Deal> deals;

    public DealsListing(ListDealsOptions filter, int total, int totalPages, List<Deal> deals) {
        this.filter = filter;
        this.total = total;
        this.totalPages = totalPages;
        this.deals = deals;
    }

}
