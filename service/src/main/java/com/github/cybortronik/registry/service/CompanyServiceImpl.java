package com.github.cybortronik.registry.service;

import com.github.cybortronik.registry.bean.Company;
import com.github.cybortronik.registry.repository.CompanyRepository;
import com.github.cybortronik.registry.repository.bean.FilterRequest;
import com.github.cybortronik.registry.repository.bean.FilterResult;

import javax.inject.Inject;

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
}
