package model;

import java.util.List;

/**
 * TODO make immutable
 * */
public class DealsListing {
    public ListDealsOptions filter;
    public int total;
    public int totalPages;
    public List<Deal> deals;

}
