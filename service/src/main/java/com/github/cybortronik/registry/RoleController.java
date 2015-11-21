package com.github.cybortronik.registry;

import com.github.cybortronik.registry.bean.Role;
import com.github.cybortronik.registry.service.RoleService;
import spark.Request;
import spark.Response;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by stanislav on 11/21/15.
 */
public class RoleController {

    private RoleService roleService;
    private JsonTransformer jsonTransformer;

    @Inject
    public RoleController(RoleService roleService, JsonTransformer jsonTransformer) {
        this.roleService = roleService;
        this.jsonTransformer = jsonTransformer;
    }

    public List<Role> getRoles(Request request, Response response) {
        return roleService.getRoles();
    }

    public Role create(Request request, Response response) {
        String body = request.body();
        Role role = jsonTransformer.fromJson(body, Role.class);
        return roleService.create(role.getName());
    }

    public String delete(Request request, Response response) {
        String name = request.params(":name");
        roleService.delete(name);
        response.status(204);
        return "";
    }
}
