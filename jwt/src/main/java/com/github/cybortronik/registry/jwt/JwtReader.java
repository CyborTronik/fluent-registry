package com.github.cybortronik.registry.jwt;

import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;

import java.security.PublicKey;

/**
 * Created by stanislav on 11/17/15.
 */
public class JwtReader {

    private PublicKey key;

    public JwtReader(PublicKey key) {
        this.key = key;
    }

    public JwtClaimsAdapter read(String jwt) {
        JwtConsumer jwtConsumer = new JwtConsumerBuilder()
                .setRequireExpirationTime()
                .setAllowedClockSkewInSeconds(30)
                .setRequireSubject()
                .setExpectedIssuer("registry")
                .setVerificationKey(key)
                .build();

        try {
            JwtClaims jwtClaims = jwtConsumer.processToClaims(jwt);
            return new JwtClaimsAdapter(jwtClaims);
        } catch (InvalidJwtException e) {
            throw new RuntimeException(e);
        }
    }
}