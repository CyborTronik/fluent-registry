package com.github.cybortronik.registry.controller;

import com.github.cybortronik.registry.JsonTransformer;
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
    private JsonTransformer jsonTransformer;

    @Inject
    public CompaniesController(CompanyService companyService, FilterRequestFactory filterRequestFactory, JsonTransformer jsonTransformer) {
        this.companyService = companyService;
        this.filterRequestFactory = filterRequestFactory;
        this.jsonTransformer = jsonTransformer;
    }

    public FilterResult<Company> getCompanies(Request request, Response response) {
        FilterRequest filterRequest = filterRequestFactory.create(request);
        return companyService.filter(filterRequest);
    }

    public Company create(Request request, Response response) {
        String body = request.body();
        Company company = jsonTransformer.fromJson(body, Company.class);
        return companyService.createCompany(company);
    }
}
