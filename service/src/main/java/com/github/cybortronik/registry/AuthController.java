package com.github.cybortronik.registry;

import com.github.cybortronik.registry.bean.Login;
import com.github.cybortronik.registry.bean.LoginToken;
import com.github.cybortronik.registry.bean.User;
import com.github.cybortronik.registry.jwt.JwtClaimsAdapter;
import com.github.cybortronik.registry.jwt.JwtReader;
import com.github.cybortronik.registry.service.UserService;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.lang.JoseException;
import spark.Request;
import spark.Response;

import javax.inject.Inject;

import java.security.PublicKey;

import static spark.Spark.halt;

/**
 * Created by stanislav on 11/6/15.
 */
public class AuthController {

    private UserService userService;
    private JsonTransformer jsonTransformer;
    private JwtGenerator jwtGenerator;
    private JwtReader jwtReader;

    @Inject
    public AuthController(UserService userService, JsonTransformer jsonTransformer, JwtGenerator jwtGenerator, JwtReader jwtReader) {
        this.userService = userService;
        this.jsonTransformer = jsonTransformer;
        this.jwtGenerator = jwtGenerator;
        this.jwtReader = jwtReader;
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

    public void authenticate(Request request, Response response) throws MalformedClaimException {
        User user  = request.session().attribute("user");
        if (user == null) {
            String jwt = request.headers("JWT");
            JwtClaimsAdapter read = jwtReader.read(jwt);
            if (read.getIssuer().equals("registry")){
                request.session().attribute("user", read.readUser());
            } else {
                halt(401, "You have no rights to this action");
            }
        }
    }
}
