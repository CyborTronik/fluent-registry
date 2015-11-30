package com.github.cybortronik.registry.repository;

import com.github.cybortronik.registry.bean.Company;
import com.github.cybortronik.registry.repository.bean.FilterRequest;
import com.github.cybortronik.registry.repository.bean.FilterResult;

import java.util.UUID;

/**
 * Created by stanislav on 11/17/15.
 */
public interface CompanyRepository extends Repository<Company, UUID> {
    FilterResult<Company> filter(FilterRequest filterRequest);
}
