package com.github.cybortronik.registry.repository.sql2o;

import com.github.cybortronik.registry.bean.User;
import com.github.cybortronik.registry.repository.UserRepository;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by stanislav on 10/28/15.
 */
public class UserRepositoryImpl implements UserRepository {

    public static final String TABLE_NAME = "users";
    private DbExecutor dbExecutor;

    @Inject
    public UserRepositoryImpl(DbExecutor dbExecutor) {
        this.dbExecutor = dbExecutor;
    }

    @Override
    public User findById(String uuid) {
        return dbExecutor.findById(TABLE_NAME, uuid, User.class);
    }

    @Override
    public UUID createUser(String displayName, String email, String passwordHash) {
        UUID uuid = UUID.randomUUID();
        Map<String, Object> params = new HashMap<>();
        params.put("id", uuid.toString());
        params.put("displayName", displayName);
        params.put("email", email);
        params.put("passwordHash", passwordHash);
        dbExecutor.execute("INSERT INTO users (id, displayName, email, passwordHash, createdAt, updatedAt) VALUES(:id, :displayName, :email, :passwordHash, NULL, NULL)", params);
        return uuid;
    }

    @Override
    public User findByEmail(String email) {
        return dbExecutor.findByEmail(TABLE_NAME, email, User.class);
    }

    @Override
    public void disable(User entity) {
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
}
