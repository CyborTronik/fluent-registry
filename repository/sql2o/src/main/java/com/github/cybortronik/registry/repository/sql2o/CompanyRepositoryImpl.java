package com.github.cybortronik.registry.repository.sql2o;

import com.github.cybortronik.registry.bean.Company;
import com.github.cybortronik.registry.repository.CompanyRepository;

import javax.inject.Inject;
import java.util.UUID;

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
}
