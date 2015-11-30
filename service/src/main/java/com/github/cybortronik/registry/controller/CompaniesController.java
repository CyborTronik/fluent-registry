package com.github.cybortronik.registry.controller;

import com.github.cybortronik.registry.bean.Company;
import com.github.cybortronik.registry.bean.FilterRequestFactory;
import com.github.cybortronik.registry.repository.bean.FilterRequest;
import com.github.cybortronik.registry.repository.bean.FilterResult;
import com.github.cybortronik.registry.service.CompanyService;
import spark.Request;
import spark.Response;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by stanislav on 11/17/15.
 */
public class CompaniesController {

    private CompanyService companyService;
    private FilterRequestFactory filterRequestFactory;

    @Inject
    public CompaniesController(CompanyService companyService, FilterRequestFactory filterRequestFactory) {
        this.companyService = companyService;
        this.filterRequestFactory = filterRequestFactory;
    }

    public FilterResult<Company> getCompanies(Request request, Response response) {
        FilterRequest filterRequest = filterRequestFactory.create(request);
        return companyService.filter(filterRequest);
    }
}
