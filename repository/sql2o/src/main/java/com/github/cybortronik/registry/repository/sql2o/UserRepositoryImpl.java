package com.github.cybortronik.registry.repository.sql2o;

import com.github.cybortronik.registry.bean.User;
import com.github.cybortronik.registry.repository.bean.FilterResult;
import com.github.cybortronik.registry.repository.bean.FilterRequest;
import com.github.cybortronik.registry.repository.UserRepository;
import com.google.gson.JsonElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.*;

import static com.github.cybortronik.registry.repository.sql2o.InjectionUtils.filterInjectionValue;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Created by stanislav on 10/28/15.
 */
public class UserRepositoryImpl implements UserRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserRepositoryImpl.class);

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
    public UUID createUser(String displayName, String email, String passwordHash, String details, String companyId) {
        UUID uuid = UUID.randomUUID();
        Map<String, Object> params = new HashMap<>();
        params.put("id", uuid.toString());
        params.put("displayName", displayName);
        params.put("email", email);
        params.put("passwordHash", passwordHash);
        params.put("details", details);
        params.put("companyId", companyId);
        dbExecutor.execute("INSERT INTO users (id, displayName, email, passwordHash, details, companyId, createdAt, updatedAt) VALUES(:id, :displayName, :email, :passwordHash, :details, :companyId, NULL, NULL)", params);
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
    public FilterResult<User> filter(FilterRequest filterRequest) {
        int limit = filterRequest.getLimit();
        if (limit <= 0)
            throw new IllegalArgumentException("Cannot filter for items less or equals to zero");
        String sql = computeFilterQuery(filterRequest);

        long countFilteredUsers = dbExecutor.countBySql(sql);
        int totalPages = (int) ((countFilteredUsers + limit)/limit);

        sql = computePageView(filterRequest, limit, sql);
        LOGGER.trace("Generated SQL: " + sql);

        List<User> users = dbExecutor.find(sql, new HashMap<>(), User.class);
        FilterResult<User> filterResult = new FilterResult<>();
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
        String sql = "select * from users where enabled=1 ";
        String query = filterRequest.getQuery();
        if (isNotBlank(query)) {
            String safeQuery = filterInjectionValue(query);
            sql += " AND displayName like '%" + safeQuery + "%' OR email like '%" + safeQuery + "%' ";
        }
        return sql;
    }

    @Override
    public UUID createUser(String displayName, String email, String passwordHash) {
        return createUser(displayName, email, passwordHash, null, null);
    }

    @Override
    public void updateDetails(String uuid, JsonElement details) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", uuid);
        params.put("details", details.toString());
        dbExecutor.execute("update users set details=:details where id = :id", params);
    }

    @Override
    public void updateCompanyId(String uuid, String companyId) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", uuid);
        params.put("companyId", companyId);
        dbExecutor.execute("update users set companyId=:companyId where id=:id", params);
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
