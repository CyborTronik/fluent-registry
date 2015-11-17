package com.github.cybortronik.registry;

import com.github.cybortronik.registry.service.UserService;
import com.github.cybortronik.registry.service.UserServiceImpl;
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
    }
}
