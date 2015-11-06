package com.github.cybortronik.registry;

import com.github.cybortronik.registry.bean.User;
import com.github.cybortronik.registry.exception.InvalidRequestException;
import com.github.cybortronik.registry.service.UserService;
import spark.Request;
import spark.Response;

import javax.inject.Inject;
import java.util.List;

import static org.eclipse.jetty.util.StringUtil.isBlank;

/**
 * Created by stanislav on 10/27/15.
 */
public class UsersController {

    private UserService userService;

    @Inject
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    public List<User> getUsers(Request request, Response response) {
        return null;
    }

    public User showUser(Request request, Response response) {
        String uuid = request.params(":uuid");
        if (isBlank(uuid)) {
            throw new InvalidRequestException("Cannot find a user without identifier");
        }
        return userService.findById(uuid);
    }

    public User createUser(Request request, Response response) {
        return null;
    }

    public User updateUser(Request request, Response response) {
        return null;
    }

    public User deleteUser(Request request, Response response) {
        return null;
    }
}
