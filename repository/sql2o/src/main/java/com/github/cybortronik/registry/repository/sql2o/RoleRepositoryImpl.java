package com.github.cybortronik.registry.repository.sql2o;

import com.github.cybortronik.registry.bean.Role;
import com.github.cybortronik.registry.repository.RoleRepository;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

/**
 * Created by stanislav on 11/21/15.
 */
public class RoleRepositoryImpl implements RoleRepository {

    public static final String TABLE_NAME = "roles";
    private final DbExecutor dbExecutor;

    @Inject
    public RoleRepositoryImpl(DbExecutor dbExecutor) {
        this.dbExecutor = dbExecutor;
    }

    @Override
    public void deleteAll() {
        dbExecutor.deleteFrom(TABLE_NAME);
    }

    @Override
    public List<Role> getRoles() {
        return dbExecutor.findAll(TABLE_NAME, Role.class);
    }

    @Override
    public long countRoles() {
        return dbExecutor.countAll(TABLE_NAME);
    }

    @Override
    public Role create(String name) {
        dbExecutor.execute("insert into roles (name, createdAt) VALUES (:name, NULL)", Collections.singletonMap("name", name));
        return findByName(name);
    }

    @Override
    public void delete(String name) {
        dbExecutor.execute("update roles SET enabled=0 WHERE name=:name", Collections.singletonMap("name", name) );
    }

    private Role findByName(String name) {
        return dbExecutor.findByName(TABLE_NAME, name, Role.class);
    }
}
