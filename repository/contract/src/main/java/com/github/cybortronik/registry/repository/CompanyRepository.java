package com.github.cybortronik.registry.repository;

import com.github.cybortronik.registry.bean.Company;
import com.github.cybortronik.registry.repository.bean.FilterRequest;
import com.github.cybortronik.registry.repository.bean.FilterResult;

import java.util.UUID;

/**
 * Created by stanislav on 11/17/15.
 */
public interface CompanyRepository extends Repository<Company, String> {
    FilterResult<Company> filter(FilterRequest filterRequest);

    void createCompany(String companyName);

    String createCompany(Company company);

    void updateName(String uuid, String companyName);

    void updateLogoPath(String companyUuid, String logoPath);

    void updateDescription(String companyUuid, String description);

    void updateDetails(String companyUuid, String details);
}
