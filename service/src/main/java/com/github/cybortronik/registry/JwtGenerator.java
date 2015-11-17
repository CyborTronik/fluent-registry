package com.github.cybortronik.registry;

import com.github.cybortronik.registry.bean.User;
import com.github.cybortronik.registry.jwt.JsonKeyLoader;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.lang.JoseException;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.PrivateKey;

import static com.github.cybortronik.registry.jwt.JwtClaimsBuilder.asJwtClaims;

/**
 * Created by stanislav on 11/6/15.
 */
public class JwtGenerator {

    private PrivateKey privateKey;
    private JsonTransformer jsonTransformer;

    @Inject
    public JwtGenerator(JsonTransformer jsonTransformer) throws IOException, JoseException {
        this.jsonTransformer = jsonTransformer;
        privateKey = loadPrivateKey();
    }

    private PrivateKey loadPrivateKey() throws IOException, JoseException {
        String filePathToKey = System.getProperty("key.path");
        Path keyFilePath = Paths.get(filePathToKey);
        return new JsonKeyLoader().loadPrivateKeyFromPath(keyFilePath);
    }

    public String generateToken(User user) throws JoseException {
        JwtClaims jwtClaims= asJwtClaims()
                .withIssuer("registry")
                .withExpirationMinutes(10)
                .withSubject("authentication")
                .withClaim("user", jsonTransformer.toJson(user))
                .build();

        return computeJwt(jwtClaims);
    }

    private String computeJwt(JwtClaims jwtClaims) throws JoseException {
        JsonWebSignature jws = new JsonWebSignature();
        jws.setPayload(jwtClaims.toJson());
        jws.setKey(privateKey);
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);
        return jws.getCompactSerialization();
    }
}
