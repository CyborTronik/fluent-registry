package com.github.cybortronik.registry;

import com.github.cybortronik.registry.bean.Login;
import com.github.cybortronik.registry.bean.LoginToken;
import com.github.cybortronik.registry.bean.User;
import com.github.cybortronik.registry.service.UserService;
import org.jose4j.lang.JoseException;
import spark.Request;
import spark.Response;

import javax.inject.Inject;

import static spark.Spark.halt;

/**
 * Created by stanislav on 11/6/15.
 */
public class AuthController {

    private UserService userService;
    private JsonTransformer jsonTransformer;
    private JwtGenerator jwtGenerator;

    @Inject
    public AuthController(UserService userService, JsonTransformer jsonTransformer, JwtGenerator jwtGenerator) {
        this.userService = userService;
        this.jsonTransformer = jsonTransformer;
        this.jwtGenerator = jwtGenerator;
    }

    public LoginToken login(Request request, Response response) throws JoseException {
        String body = request.body();
        Login login = jsonTransformer.fromJson(body, Login.class);
        User user = userService.find(login);
        if (user == null)
            halt(401, "Invalid credentials");
        String token = jwtGenerator.generateToken(user);
        return new LoginToken(token);
    }
}
