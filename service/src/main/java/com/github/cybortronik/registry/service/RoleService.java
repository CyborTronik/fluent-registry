package com.github.cybortronik.registry.service;

import com.github.cybortronik.registry.bean.Role;

import java.util.List;

/**
 * Created by stanislav on 11/21/15.
 */
public interface RoleService {
    List<Role> getRoles();

    Role create(String name);

    void delete(String name);
}
