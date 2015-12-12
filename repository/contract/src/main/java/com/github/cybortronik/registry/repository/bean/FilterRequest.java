package com.github.cybortronik.registry.repository.bean;

/**
 * Created by stanislav on 11/23/15.
 */
public class FilterRequest {
    public static final int MAX_ITEMS_PER_PAGE = 250;
    public static final int MIN_ITEMS_PER_PAGE = 5;
    private String query;
    private String orderBy;
    private int page;
    private int limit = 50;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        if (limit > MAX_ITEMS_PER_PAGE)
            limit = MAX_ITEMS_PER_PAGE;
        if (limit < MIN_ITEMS_PER_PAGE)
            limit = MIN_ITEMS_PER_PAGE;
        this.limit = limit;
    }
}
