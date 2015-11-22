package com.github.cybortronik.registry;

import com.github.cybortronik.registry.bean.Roles;
import com.github.cybortronik.registry.repository.sql2o.Sql2oModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

import static spark.Spark.*;

/**
 * Created by stanislav on 10/26/15.
 */
public class Application {

    public static final String ACCEPT_TYPE = "application/json";

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new Sql2oModule(), new SecurityModule(), new RegistryModule());
        JsonTransformer jsonTransformer = injector.getInstance(JsonTransformer.class);

        AuthController authController = injector.getInstance(AuthController.class);
        post("/login", ACCEPT_TYPE, (authController::login), jsonTransformer::toJson);

        before("/users", ((request, response) -> authController.authenticate(request, response, Roles.MANAGE_USERS)));

        CompaniesController companiesController = injector.getInstance(CompaniesController.class);
        get("/companies", ACCEPT_TYPE,(companiesController::getCompanies), jsonTransformer::toJson);

        UsersController usersController = injector.getInstance(UsersController.class);
        get("/users", ACCEPT_TYPE, (usersController::getUsers), jsonTransformer::toJson);
        get("/users/:uuid", ACCEPT_TYPE, (usersController::showUser), jsonTransformer::toJson);
        put("/users", ACCEPT_TYPE, (usersController::createUser), jsonTransformer::toJson);
        post("/users", ACCEPT_TYPE, (usersController::updateUser), jsonTransformer::toJson);
        delete("/users", ACCEPT_TYPE, (usersController::deleteUser), jsonTransformer::toJson);


        RoleController  roleController = injector.getInstance(RoleController.class);
        get("/roles", ACCEPT_TYPE,(roleController::getRoles), jsonTransformer::toJson);
        put("/roles", ACCEPT_TYPE,(roleController::create), jsonTransformer::toJson);
        delete("/roles/:name", ACCEPT_TYPE,(roleController::delete), jsonTransformer::toJson);
    }
}
