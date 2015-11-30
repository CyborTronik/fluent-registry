package com.github.cybortronik.registry.service;

import com.github.cybortronik.registry.bean.Company;
import com.github.cybortronik.registry.repository.bean.FilterRequest;
import com.github.cybortronik.registry.repository.bean.FilterResult;

/**
 * Created by stanislav on 11/30/15.
 */
public interface CompanyService {
    FilterResult<Company> filter(FilterRequest filterRequest);

    void deleteAll();

    void createCompany(String companyName);
}
