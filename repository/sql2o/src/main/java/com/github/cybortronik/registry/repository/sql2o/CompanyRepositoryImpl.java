package com.github.cybortronik.registry.repository.sql2o;

import com.github.cybortronik.registry.bean.Company;
import com.github.cybortronik.registry.bean.User;
import com.github.cybortronik.registry.repository.CompanyRepository;
import com.github.cybortronik.registry.repository.bean.FilterRequest;
import com.github.cybortronik.registry.repository.bean.FilterResult;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
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
    public Company findById(UUID uuid) {
        return null;
    }

    @Override
    public void disable(Company entity) {

    }

    @Override
    public void disableById(UUID uuid) {

    }

    @Override
    public void deleteAll() {
        dbExecutor.deleteFrom(TABLE_NAME);
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
        if (isNotBlank(filterRequest.getSortBy()))
            sql += " ORDER BY " + filterInjectionValue(filterRequest.getSortBy());
        int offset = filterRequest.getPage() * limit;
        sql += " LIMIT " + offset + "," + limit;
        return sql;
    }

    private String computeFilterQuery(FilterRequest filterRequest) {
        String sql = "select * from companies ";
        String query = filterRequest.getQuery();
        if (isNotBlank(query)) {
            String safeQuery = filterInjectionValue(query);
            sql += " WHERE name like '%" + safeQuery + "%' OR description like '%" + safeQuery + "%' ";
        }
        return sql;
    }
}
