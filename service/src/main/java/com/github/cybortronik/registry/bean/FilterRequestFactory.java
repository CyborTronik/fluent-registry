package com.github.cybortronik.registry.bean;

import com.github.cybortronik.registry.UrlDecoder;
import com.github.cybortronik.registry.repository.bean.FilterRequest;
import spark.Request;

import javax.inject.Inject;

/**
 * Created by stanislav on 11/30/15.
 */
public class FilterRequestFactory {

    private UrlDecoder urlDecoder;

    @Inject
    public FilterRequestFactory(UrlDecoder urlDecoder) {
        this.urlDecoder = urlDecoder;
    }

    public FilterRequest create(Request request) {
        FilterRequest filterRequest = new FilterRequest();

        String displayName = request.queryParams("q");
        displayName = urlDecoder.decode(displayName);
        filterRequest.setQuery(displayName);

        String sortBy = request.queryParams("sortBy");
        sortBy = urlDecoder.decode(sortBy);
        filterRequest.setSortBy(sortBy);

        String page = request.queryParams("page");
        if (page != null)
            filterRequest.setPage(Integer.parseInt(page));

        String limit = request.queryParams("limit");
        if (limit != null)
            filterRequest.setLimit(Integer.parseInt(limit));

        return filterRequest;
    }
}
