package com.github.cybortronik.registry.repository.sql2o;

import com.github.cybortronik.registry.bean.Role;
import com.github.cybortronik.registry.bean.User;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.util.Objects.nonNull;

/**
 * Created by stanislav on 10/28/15.
 */
public class DbExecutor {

    public static final String SELECT_BY_ID = "SELECT * FROM %s WHERE id=:id";
    public static final String DELETE_ALL = "DELETE FROM %s ";
    public static final String DISABLE_BY_ID = "UPDATE %s SET enabled=0 WHERE id=:id ";
    private static final java.lang.String SELECT_BY_EMAIL = "SELECT * FROM %s WHERE email=:email";
    private static final java.lang.String SELECT_BY_NAME = "SELECT * FROM %s WHERE name=:name";
    public static final String SELECT_ALL = "SELECT * FROM %s WHERE enabled=1 ";
    private Sql2o sql2o;

    @Inject
    public DbExecutor(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    public void deleteFrom(String tableName) {
        try (Connection con = sql2o.open()) {
            con.createQuery(format(DELETE_ALL, tableName))
                    .executeUpdate();
            con.commit();
        }
    }

    public <T> T findById(String tableName, String id, Class<T> tClass) {
        try (Connection con = sql2o.open()) {
            T entity = con.createQuery(format(SELECT_BY_ID, tableName))
                    .addParameter("id", id)
                    .executeAndFetchFirst(tClass);
            con.commit();
            return entity;
        }
    }

    public void disableById(String tableName, String uuid) {
        try (Connection con = sql2o.open()) {
            con.createQuery(format(DISABLE_BY_ID, tableName))
                    .addParameter("id", uuid)
                    .executeUpdate();
            con.commit();
        }
    }

    public void execute(String sql, Map<String, Object> params) {
        try (Connection con = sql2o.open()) {
            Query query = con.createQuery(sql);
            for (Map.Entry<String, Object> entry : params.entrySet())
                query = query.addParameter(entry.getKey(), entry.getValue());
            query.executeUpdate();
            con.commit();
        } catch (Sql2oException e) {
            String joinParams = String.join(",", params.entrySet().stream().map(Object::toString).collect(Collectors.toList()));
            throw new RuntimeException("Exception for " + sql + " with params: " + joinParams, e);
        }
    }

    public <T> T findByEmail(String tableName, String email, Class<T> tClass) {
        try (Connection con = sql2o.open()) {
            T entity = con.createQuery(format(SELECT_BY_EMAIL, tableName))
                    .addParameter("email", email)
                    .executeAndFetchFirst(tClass);
            con.commit();
            return entity;
        }
    }

    public <T> List<T> findAll(String tableName, Class<T> tClass) {
        try (Connection con = sql2o.open()) {
            List<T> list = con.createQuery(format(SELECT_ALL, tableName))
                    .executeAndFetch(tClass);
            con.commit();
            return list;
        }
    }

    public long countAll(String tableName) {
        try (Connection con = sql2o.open()) {
            Object o = con.createQuery(format("SELECT count(*) FROM %s", tableName))
                    .executeScalar();
            con.commit();
            return ((Number)o).longValue();
        }
    }

    public <T> T findByName(String tableName, String name, Class<T> tClass) {
        try (Connection con = sql2o.open()) {
            T entity = con.createQuery(format(SELECT_BY_NAME, tableName))
                    .addParameter("name", name)
                    .executeAndFetchFirst(tClass);
            con.commit();
            return entity;
        }
    }

    public <T> List<T> findScalars(String sql, Map<String, Object> params, Class<T> tClass) {
        try (Connection con = sql2o.open()) {
            Query query = con.createQuery(sql);
            for (Map.Entry<String, Object> entry : params.entrySet())
                query = query.addParameter(entry.getKey(), entry.getValue());
            List<T> list = query.executeScalarList(tClass);
            con.commit();
            return list;
        }
    }

    public <T> List<T> find(String sql, Map<String, Object> params, Class<T> tClass) {
        try (Connection con = sql2o.open()) {
            Query query = con.createQuery(sql);
            for (Map.Entry<String, Object> entry : params.entrySet())
                    query = query.addParameter(entry.getKey(), entry.getValue());
            List<T> list = query.executeAndFetch(tClass);
            con.commit();
            return list;
        }
    }

    public long countBySql(String sql) {
        try (Connection con = sql2o.open()) {
            Object o = con.createQuery(format("SELECT count(*) FROM (%s) as tmp", sql))
                    .executeScalar();
            con.commit();
            return ((Number)o).longValue();
        }
    }
}
