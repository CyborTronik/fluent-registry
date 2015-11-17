package com.github.cybortronik.registry.jwt;

import org.jose4j.jwk.PublicJsonWebKey;
import org.jose4j.lang.JoseException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.List;

/**
 * Created by stanislav on 11/17/15.
 */
public class JsonKeyLoader {

    public PrivateKey loadPrivateKey(String jsonKey) throws JoseException {
        PublicJsonWebKey webKey = loadWebKey(jsonKey);
        return webKey.getPrivateKey();
    }

    public PublicJsonWebKey loadWebKey(String jsonKey) throws JoseException {
        return PublicJsonWebKey.Factory.newPublicJwk(jsonKey);
    }

    public PrivateKey loadPrivateKeyFromPath(Path path) throws JoseException, IOException {
        List<String> lines = Files.readAllLines(path);
        String jsonKey = String.join(" ", lines);
        return loadPrivateKey(jsonKey);
    }

    public PublicKey loadPublicKey(Path path) throws IOException, JoseException {
        List<String> lines = Files.readAllLines(path);
        String jsonKey = String.join(" ", lines);
        return loadPublicKey(jsonKey);
    }

    private PublicKey loadPublicKey(String jsonKey) throws JoseException {
        return loadWebKey(jsonKey).getPublicKey();
    }
}
