package com.github.cybortronik.registry;

import com.github.cybortronik.registry.jwt.JsonKeyLoader;
import com.github.cybortronik.registry.jwt.JwtReader;
import com.google.inject.AbstractModule;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * Created by stanislav on 11/17/15.
 */
public class SecurityModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(PrivateKey.class).toInstance(loadPrivateKey());
        PublicKey publicKey = loadPublicKey();
        bind(PublicKey.class).toInstance(publicKey);
        bind(JwtReader.class).toInstance(new JwtReader(publicKey));
    }

    private PublicKey loadPublicKey() {
        String filePathToKey = System.getProperty("key.path");
        Path keyFilePath = Paths.get(filePathToKey);
        try {
            return new JsonKeyLoader().loadPublicKey(keyFilePath);
        } catch (Exception e) {
            throw new RuntimeException("Cannot load public key ", e);
        }
    }

    private PrivateKey loadPrivateKey() {
        String filePathToKey = System.getProperty("key.path");
        Path keyFilePath = Paths.get(filePathToKey);
        try {
            return new JsonKeyLoader().loadPrivateKeyFromPath(keyFilePath);
        } catch (Exception e) {
            throw new RuntimeException("Cannot load private key ", e);
        }
    }
}
