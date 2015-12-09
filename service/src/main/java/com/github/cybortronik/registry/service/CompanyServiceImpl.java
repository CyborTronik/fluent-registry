package com.github.cybortronik.registry.service;

import com.github.cybortronik.registry.bean.Company;
import com.github.cybortronik.registry.repository.CompanyRepository;
import com.github.cybortronik.registry.repository.bean.FilterRequest;
import com.github.cybortronik.registry.repository.bean.FilterResult;

import javax.inject.Inject;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Created by stanislav on 11/30/15.
 */
public class CompanyServiceImpl implements CompanyService {

    private CompanyRepository companyRepository;

    @Inject
    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public FilterResult<Company> filter(FilterRequest filterRequest) {
        return companyRepository.filter(filterRequest);
    }

    @Override
    public void deleteAll() {
        companyRepository.deleteAll();
    }

    @Override
    public void createCompany(String companyName) {
        companyRepository.createCompany(companyName);
    }

    @Override
    public Company createCompany(Company company) {
        String uuid = companyRepository.createCompany(company);
        return companyRepository.findById(uuid);
    }

    @Override
    public Company updateCompany(String companyUuid, Company company) {
        if (isNotBlank(company.getName()))
            companyRepository.updateName(companyUuid, company.getName());
        if (isNotBlank(company.getLogoPath()))
            companyRepository.updateLogoPath(companyUuid, company.getLogoPath());
        if (isNotBlank(company.getDescription()))
            companyRepository.updateDescription(companyUuid, company.getDescription());
        if (company.getDetails() != null)
            companyRepository.updateDetails(companyUuid, company.getDetails().toString());
        return companyRepository.findById(companyUuid);
    }

    @Override
    public Company findById(String companyId) {
        return companyRepository.findById(companyId);
    }

    @Override
    public void delete(String companyId) {
        companyRepository.disableById(companyId);
    }
}
