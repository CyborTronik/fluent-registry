package com.github.cybortronik.registry.controller;

import com.github.cybortronik.registry.JsonTransformer;
import com.github.cybortronik.registry.bean.FilterRequestFactory;
import com.github.cybortronik.registry.bean.User;
import com.github.cybortronik.registry.bean.UserRequest;
import com.github.cybortronik.registry.repository.bean.FilterRequest;
import com.github.cybortronik.registry.repository.bean.FilterResult;
import com.github.cybortronik.registry.service.UserService;
import spark.Request;
import spark.Response;

import javax.inject.Inject;

import static java.util.Objects.isNull;
import static org.eclipse.jetty.util.StringUtil.isBlank;
import static spark.Spark.halt;

/**
 * Created by stanislav on 10/27/15.
 */
public class UsersController {

    private UserService userService;
    private JsonTransformer jsonTransformer;
    private FilterRequestFactory filterRequestFactory;

    @Inject
    public UsersController(UserService userService, JsonTransformer jsonTransformer, FilterRequestFactory filterRequestFactory) {
        this.userService = userService;
        this.jsonTransformer = jsonTransformer;
        this.filterRequestFactory = filterRequestFactory;
    }

    public FilterResult<User> getUsers(Request request, Response response) {
        FilterRequest filterRequest = filterRequestFactory.create(request);
        return userService.filter(filterRequest);
    }

    public User showUser(Request request, Response response) {
        String uuid = getUserUuid(request);
        User user = userService.findById(uuid);
        requiredUser(user);
        return user;
    }

    private String getUserUuid(Request request) {
        String uuid = request.params(":uuid");
        if (isBlank(uuid)) {
            halt(400, "Cannot find a user without identifier");
        }
        return uuid;
    }

    public User createUser(Request request, Response response) {
        UserRequest userRequest = jsonTransformer.fromJson(request.body(), UserRequest.class);
        return userService.createUser(userRequest);
    }

    public User updateUser(Request request, Response response) {
        String uuid = getUserUuid(request);
        UserRequest userRequest = jsonTransformer.fromJson(request.body(), UserRequest.class);
        User user = userService.updateUser(uuid, userRequest);
        requiredUser(user);
        return user;
    }

    private void requiredUser(User user) {
        if (isNull(user))
            halt(400, "User not found please check the request");
    }

    public String deleteUser(Request request, Response response) {
        String uuid = getUserUuid(request);
        userService.delete(uuid);
        User user = userService.findById(uuid);
        requiredUser(user);
        halt(204);
        return "";
    }
}
