package com.github.cybortronik.registry.repository.sql2o;

import com.github.cybortronik.registry.bean.User;
import com.github.cybortronik.registry.repository.UserFilter;
import com.github.cybortronik.registry.repository.UserRepository;

import javax.inject.Inject;
import java.util.*;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

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
        User user = dbExecutor.findById(TABLE_NAME, uuid, User.class);
        loadRoles(user);
        return user;
    }

    private void loadRoles(User user) {
        if (user == null)
            return;
        String userId = user.getId();
        List<String> roles = fetchRoles(userId);
        user.setRoles(roles);
    }

    private List<String> fetchRoles(String userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("user_id", userId);
        return dbExecutor.findScalars("select role_name from user_roles where user_id=:user_id ", params, String.class);
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
        User user = dbExecutor.findByEmail(TABLE_NAME, email, User.class);
        loadRoles(user);
        return user;
    }

    @Override
    public void addUserRole(String userId, String role) {
        Map<String, Object> params = new HashMap<>();
        params.put("user_id", userId);
        params.put("role_name", role);
        dbExecutor.execute("insert into user_roles (user_id, role_name) VALUES (:user_id, :role_name)", params);
    }

    @Override
    public void updateDisplayName(String uuid, String displayName) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", uuid);
        params.put("displayName", displayName);
        dbExecutor.execute("update users set displayName=:displayName where id = :id", params);
    }

    @Override
    public void updateEmail(String uuid, String email) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", uuid);
        params.put("email", email);
        dbExecutor.execute("update users set email=:email where id = :id", params);
    }

    @Override
    public void setRoles(String uuid, List<String> roles) {
        List<String> currentRoles = fetchRoles(uuid);
        List<String> rolesToDelete = new ArrayList<>(currentRoles);
        rolesToDelete.removeAll(roles);
        rolesToDelete.forEach(role -> deleteRole(uuid, role));

        List<String> rolesToAdd = new ArrayList<>(roles);
        rolesToAdd.removeAll(currentRoles);
        rolesToAdd.forEach(role -> addUserRole(uuid, role));
    }

    @Override
    public void setPasswordHash(String uuid, String passwordHash) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", uuid);
        params.put("passwordHash", passwordHash);
        dbExecutor.execute("update users set passwordHash=:passwordHash where id = :id", params);
    }

    @Override
    public List<User> filter(UserFilter userFilter) {
        if (userFilter.getItemsPerPage() <= 0)
            throw new IllegalArgumentException("Cannot filter for items less or equals to zero");
        Map<String, Object> params = prepareParams(userFilter);
        String sql = "select * from users ";
        for (Map.Entry<String, Object> entry : params.entrySet())
            sql += " AND " + entry.getKey() + " like '%" + entry.getValue() + "%' ";
        if (isNotBlank(userFilter.getSortBy()))
            sql += " ORDER BY " + filterInjection(userFilter.getSortBy());
        sql += " LIMIT " + userFilter.getItemsPerPage() + "," + userFilter.getPage() * userFilter.getItemsPerPage();
        return dbExecutor.find(sql, params, User.class);
    }

    private String filterInjection(String text) {
        //Do not allow to split commands
        //Very basic injector checker
        return text.replaceAll(";", "").replaceAll("DELIMITER", "").replaceAll("//", "");
    }

    private Map<String, Object> prepareParams(UserFilter userFilter) {
        Map<String, Object> params = new HashMap<>();
        if (isNotBlank(userFilter.getDisplayName()))
            params.put("displayName", filterInjection(userFilter.getDisplayName()));
        if (isNotBlank(userFilter.getEmail()))
            params.put("email", filterInjection(userFilter.getEmail()));
        return params;
    }

    private void deleteRole(String uuid, String role) {
        Map<String, Object> params = new HashMap<>();
        params.put("user_id", uuid);
        params.put("role_name", role);
        dbExecutor.execute("delete from user_roles where user_id=:user_id AND role_name=:role_name", params);
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
        dbExecutor.deleteFrom("user_roles");
    }
}
