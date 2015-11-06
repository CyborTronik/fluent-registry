package com.github.cybortronik.registry;

import com.github.cybortronik.registry.service.UserService;
import com.github.cybortronik.registry.service.UserServiceImpl;
import com.google.inject.AbstractModule;

/**
 * Created by stanislav on 11/6/15.
 */
public class RegistryModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(JsonTransformer.class);
        bind(JwtGenerator.class);

        bind(UserService.class).to(UserServiceImpl.class);
        bind(UsersController.class);
    }
}
