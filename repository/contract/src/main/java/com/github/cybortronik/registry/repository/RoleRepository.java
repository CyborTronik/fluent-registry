package com.github.cybortronik.registry.repository;

import com.github.cybortronik.registry.bean.Role;

import java.util.List;

/**
 * Created by stanislav on 11/20/15.
 */
public interface RoleRepository {
    void deleteAll();

    List<Role> getRoles();

    long countRoles();

    Role create(String name);

    void delete(String role);
}
