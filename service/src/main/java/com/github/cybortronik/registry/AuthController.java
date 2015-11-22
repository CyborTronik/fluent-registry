package com.github.cybortronik.registry;

import com.github.cybortronik.registry.bean.Login;
import com.github.cybortronik.registry.bean.LoginToken;
import com.github.cybortronik.registry.bean.Roles;
import com.github.cybortronik.registry.bean.User;
import com.github.cybortronik.registry.jwt.JwtClaimsAdapter;
import com.github.cybortronik.registry.jwt.JwtReader;
import com.github.cybortronik.registry.service.UserService;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.lang.JoseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static spark.Spark.halt;

/**
 * Created by stanislav on 11/6/15.
 */
public class AuthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

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
    public void authenticate(Request request, Response response, Roles... roles) throws MalformedClaimException {
        String[] rls = Arrays.stream(roles)
                .map(Roles::toString)
                .collect(Collectors.toList())
                .toArray(new String[roles.length]);
        authenticate(request, response, rls);
    }

    public void authenticate(Request request, Response response, String... roles) throws MalformedClaimException {
        authenticate(request, response);
        User user = request.session().attribute("user");
        if (!user.hasAnyRole(roles))
            halt(401, "You are not allowed to access this resource");
    }

    public void authenticate(Request request, Response response) throws MalformedClaimException {
        User user  = request.session().attribute("user");
        if (user == null) {
            Set<String> headers = request.headers();
            String headersDesc = String.join(",", headers);
            LOGGER.info("Requested headers: " + headersDesc);
            String jwt = request.headers("JWT");
            LOGGER.info("Got JWT: " + jwt);
            if (isNotBlank(jwt)) {
                processJwtToken(request, jwt);
            } else {
                halt(401, "You have no rights to this action, please login in first.");
            }
        }
    }

    private void processJwtToken(Request request, String jwt) throws MalformedClaimException {
        JwtClaimsAdapter read = jwtReader.read(jwt);
        request.session().attribute("user", read.readUser());
    }
}
