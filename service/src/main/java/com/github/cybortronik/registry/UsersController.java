package com.github.cybortronik.registry;

import com.github.cybortronik.registry.service.UserService;

import javax.inject.Inject;

/**
 * Created by stanislav on 10/27/15.
 */
public class UsersController {

    private UserService userService;

    @Inject
    public UsersController(UserService userService) {
        this.userService = userService;
    }

}
