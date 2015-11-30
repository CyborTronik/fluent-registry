package com.github.cybortronik.registry.repository.bean;

import java.util.List;

/**
 * Created by stanislav on 11/29/15.
 */
public class FilterResult<T> {
    private List<T> entities;
    private int limit;
    private int currentPage;
    private int totalPages;

    public List<T> getEntities() {
        return entities;
    }

    public void setEntities(List<T> entities) {
        this.entities = entities;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
