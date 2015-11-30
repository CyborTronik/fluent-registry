package com.github.cybortronik.registry;

import com.github.cybortronik.registry.bean.FilterRequestFactory;
import com.github.cybortronik.registry.controller.CompaniesController;
import com.github.cybortronik.registry.controller.RoleController;
import com.github.cybortronik.registry.controller.UsersController;
import com.github.cybortronik.registry.service.*;
import com.google.inject.AbstractModule;
import org.jasypt.util.password.PasswordEncryptor;
import org.jasypt.util.password.rfc2307.RFC2307SMD5PasswordEncryptor;

/**
 * Created by stanislav on 11/6/15.
 */
public class RegistryModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(JsonTransformer.class);
        bind(JwtGenerator.class);

        bind(PasswordEncryptor.class).to(RFC2307SMD5PasswordEncryptor.class);

        bind(UserService.class).to(UserServiceImpl.class);
        bind(UsersController.class);

        bind(RoleService.class).to(RoleServiceImpl.class);
        bind(RoleController.class);

        bind(CompanyService.class).to(CompanyServiceImpl.class);
        bind(CompaniesController.class);

        bind(UrlDecoder.class);
        bind(FilterRequestFactory.class);
    }
}
