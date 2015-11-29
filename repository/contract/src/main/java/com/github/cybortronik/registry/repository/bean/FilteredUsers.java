package com.github.cybortronik.registry.repository.bean;

import com.github.cybortronik.registry.bean.User;

import java.util.List;

/**
 * Created by stanislav on 11/29/15.
 */
public class FilteredUsers {
    private List<User> users;
    private int limit;
    private int currentPage;
    private int totalPages;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
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
