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

import static java.util.Objects.isNull;
import static org.eclipse.jetty.util.StringUtil.isBlank;
import static spark.Spark.halt;

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

    public String delete(Request request, Response response) {
        String companyId = getCompanyUuid(request);
        companyService.delete(companyId);
        requiredCompany(companyService.findById(companyId));
        halt(204);
        return "";
    }

    public Company show(Request request, Response response) {
        String companyId = getCompanyUuid(request);
        Company updatedCompany = companyService.findById(companyId);
        requiredCompany(updatedCompany);
        return updatedCompany;
    }

    public Company update(Request request, Response response) {
        String companyId = getCompanyUuid(request);
        String body = request.body();
        Company company = jsonTransformer.fromJson(body, Company.class);
        Company updatedCompany = companyService.updateCompany(companyId, company);
        requiredCompany(updatedCompany);
        return updatedCompany;
    }

    private String getCompanyUuid(Request request) {
        String uuid = request.params(":uuid");
        if (isBlank(uuid)) {
            halt(400, "Cannot find a company without identifier");
        }
        return uuid;
    }


    private void requiredCompany(Company company) {
        if (isNull(company))
            halt(400, "Company not found please check the request");
    }
}
