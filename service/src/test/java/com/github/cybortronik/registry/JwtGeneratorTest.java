package com.github.cybortronik.registry;

import org.jose4j.jwk.EcJwkGenerator;
import org.jose4j.jwk.JsonWebKey;
import org.jose4j.jwk.PublicJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.keys.EllipticCurves;
import org.junit.Test;

import java.security.PrivateKey;
import java.security.PublicKey;

import static org.junit.Assert.*;

/**
 * Created by stanislav on 11/17/15.
 */
public class JwtGeneratorTest {

    @Test
    public void generateJWT() throws Exception {
        // Generate a new RSA key pair wrapped in a JWK
        PublicJsonWebKey rsaJwk = RsaJwkGenerator.generateJwk(2048);

        // or an EC key, if you prefer
        PublicJsonWebKey ecJwk = EcJwkGenerator.generateJwk(EllipticCurves.P256);

        // A JSON string with only the public key info
        String publicKeyJwkString = rsaJwk.toJson(JsonWebKey.OutputControlLevel.PUBLIC_ONLY);
        System.out.println(publicKeyJwkString);

        // A JSON string with both the public and private key info
        String keyPairJwkString = rsaJwk.toJson(JsonWebKey.OutputControlLevel.INCLUDE_PRIVATE);
        System.out.println(keyPairJwkString);

        // parse and convert into PublicJsonWebKey/JsonWebKey objects
        PublicJsonWebKey parsedPublicKeyJwk = PublicJsonWebKey.Factory.newPublicJwk(publicKeyJwkString);
        PublicJsonWebKey parsedKeyPairJwk = PublicJsonWebKey.Factory.newPublicJwk(keyPairJwkString);

        // the private key can be used to sign (JWS) or decrypt (JWE)
        PrivateKey privateKey = parsedKeyPairJwk.getPrivateKey();

        // the public key can be used to verify (JWS) or encrypt (JWE)
        PublicKey publicKey = parsedPublicKeyJwk.getPublicKey();
    }
}