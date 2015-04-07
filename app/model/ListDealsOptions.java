package model;

import java.util.List;
import java.util.Optional;

public class ListDealsOptions {

    public final int page;
    public final int pageSize;
    public final Optional<String> location;
    public final Optional<GeoCords> geo;
    public final Optional<String> postcode;
    public final Optional<List<String>> tags;
    public final String sort;
    public final String filter;
    public final Optional<String> focus;
    public final Optional<String> q;

    public ListDealsOptions(int page, int pageSize, Optional<String> location, Optional<GeoCords> geo,
                            Optional<String> postcode, Optional<List<String>> tags, String sort, String filter,
                            Optional<String> focus, Optional<String> q) {
        this.page = page;
        this.pageSize = pageSize;
        this.location = location;
        this.geo = geo;
        this.postcode = postcode;
        this.tags = tags;
        this.sort = sort;
        this.filter = filter;
        this.focus = focus;
        this.q = q;
    }
}
