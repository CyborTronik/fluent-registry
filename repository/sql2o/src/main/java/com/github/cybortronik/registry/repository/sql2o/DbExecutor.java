package com.github.cybortronik.registry.repository.sql2o;

import com.github.cybortronik.registry.bean.User;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;

import javax.inject.Inject;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static java.lang.String.format;

/**
 * Created by stanislav on 10/28/15.
 */
public class DbExecutor {

    public static final String SELECT_BY_ID = "SELECT * FROM %s WHERE id=:id";
    public static final String DELETE_ALL = "DELETE FROM %s";
    public static final String DISABLE_BY_ID = "UPDATE %s SET enabled=0 WHERE id=:id ";
    private static final java.lang.String SELECT_BY_EMAIL = "SELECT * FROM %s WHERE email=:email";
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
}
