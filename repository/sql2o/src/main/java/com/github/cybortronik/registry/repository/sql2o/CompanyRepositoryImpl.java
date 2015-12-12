package com.github.cybortronik.registry.repository.sql2o;

import com.github.cybortronik.registry.bean.Company;
import com.github.cybortronik.registry.repository.CompanyRepository;
import com.github.cybortronik.registry.repository.bean.FilterRequest;
import com.github.cybortronik.registry.repository.bean.FilterResult;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.github.cybortronik.registry.repository.sql2o.InjectionUtils.filterInjectionValue;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Created by stanislav on 11/17/15.
 */
public class CompanyRepositoryImpl implements CompanyRepository {

    public static final String TABLE_NAME = "companies";
    private final DbExecutor dbExecutor;

    @Inject
    public CompanyRepositoryImpl(DbExecutor dbExecutor) {
        this.dbExecutor = dbExecutor;
    }


    @Override
    public Company findById(String uuid) {
        return dbExecutor.findById(TABLE_NAME, uuid, Company.class);
    }

    @Override
    public void disable(Company entity) {
        disableById(entity.getId());
    }

    @Override
    public void disableById(String uuid) {
        dbExecutor.disableById(TABLE_NAME, uuid);
    }

    @Override
    public void deleteAll() {
        dbExecutor.deleteFrom(TABLE_NAME);
    }

    @Override
    public void createCompany(String companyName) {
        UUID uuid = UUID.randomUUID();
        Map<String, Object> params = new HashMap<>();
        params.put("id", uuid.toString());
        params.put("name", companyName);
        dbExecutor.execute("insert into companies (id, name, createdAt, updatedAt) VALUES (:id, :name, NULL, NULL)", params);
    }

    @Override
    public String createCompany(Company company) {
        UUID uuid = UUID.randomUUID();
        Map<String, Object> params = new HashMap<>();
        params.put("id", uuid.toString());
        params.put("name", company.getName());
        params.put("description", company.getDescription());
        String details = company.getDetails() != null ? company.getDetails().toString() : null;
        params.put("details", details);
        params.put("logoPath", company.getLogoPath());
        dbExecutor.execute("insert into companies (id, name, description, details, logoPath, createdAt, updatedAt) VALUES (:id, :name, :description, :details, :logoPath, NULL, NULL)", params);
        return uuid.toString();
    }

    @Override
    public void updateName(String uuid, String companyName) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", uuid);
        params.put("name", companyName);
        dbExecutor.execute("update companies set name=:name where id=:id", params);
    }

    @Override
    public void updateLogoPath(String companyUuid, String logoPath) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", companyUuid);
        params.put("logoPath", logoPath);
        dbExecutor.execute("update companies set logoPath=:logoPath where id=:id", params);
    }

    @Override
    public void updateDescription(String companyUuid, String description) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", companyUuid);
        params.put("description", description);
        dbExecutor.execute("update companies set description=:description where id=:id", params);
    }

    @Override
    public void updateDetails(String companyUuid, String details) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", companyUuid);
        params.put("details", details);
        dbExecutor.execute("update companies set details=:details where id=:id", params);
    }

    @Override
    public FilterResult<Company> filter(FilterRequest filterRequest) {
        int limit = filterRequest.getLimit();
        if (limit <= 0)
            throw new IllegalArgumentException("Cannot filter for items less or equals to zero");
        String sql = computeFilterQuery(filterRequest);

        long countFilteredUsers = dbExecutor.countBySql(sql);
        int totalPages = (int) ((countFilteredUsers + limit)/limit);

        sql = computePageView(filterRequest, limit, sql);

        List<Company> users = dbExecutor.find(sql, new HashMap<>(), Company.class);
        FilterResult<Company> filterResult = new FilterResult<>();
        filterResult.setLimit(limit);
        filterResult.setCurrentPage(filterRequest.getPage());
        filterResult.setEntities(users);
        filterResult.setTotalPages(totalPages);
        return filterResult;
    }

    private String computePageView(FilterRequest filterRequest, int limit, String sql) {
        if (isNotBlank(filterRequest.getOrderBy()))
            sql += " ORDER BY " + filterInjectionValue(filterRequest.getOrderBy());
        int offset = filterRequest.getPage() * limit;
        sql += " LIMIT " + offset + "," + limit;
        return sql;
    }

    private String computeFilterQuery(FilterRequest filterRequest) {
        String sql = "select * from companies where enabled=1 ";
        String query = filterRequest.getQuery();
        if (isNotBlank(query)) {
            String safeQuery = filterInjectionValue(query);
            sql += " AND name like '%" + safeQuery + "%' OR description like '%" + safeQuery + "%' ";
        }
        return sql;
    }
}
