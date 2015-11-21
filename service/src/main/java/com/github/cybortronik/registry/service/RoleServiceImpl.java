package com.github.cybortronik.registry.service;

import com.github.cybortronik.registry.bean.Role;
import com.github.cybortronik.registry.repository.RoleRepository;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by stanislav on 11/21/15.
 */
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    @Inject
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> getRoles() {
        return roleRepository.getRoles();
    }

    @Override
    public Role create(String name) {
        return roleRepository.create(name);
    }

    @Override
    public void delete(String name) {
        roleRepository.delete(name);
    }
}
