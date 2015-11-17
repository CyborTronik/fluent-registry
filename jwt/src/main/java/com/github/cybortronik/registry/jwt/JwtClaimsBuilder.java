package com.github.cybortronik.registry.jwt;

import org.jose4j.jwt.JwtClaims;

/**
 * Created by stanislav on 11/17/15.
 */
public class JwtClaimsBuilder {
    private JwtClaims jwtClaims = new JwtClaims();

    public static JwtClaimsBuilder asJwtClaims() {
        return new JwtClaimsBuilder();
    }

    public JwtClaimsBuilder withIssuer(String registry) {
        jwtClaims.setIssuer(registry);
        return this;
    }

    public JwtClaimsBuilder withExpirationMinutes(float minutes) {
        jwtClaims.setExpirationTimeMinutesInTheFuture(minutes);
        return this;
    }

    public JwtClaimsBuilder withSubject(String subject) {
        jwtClaims.setSubject(subject);
        return this;
    }

    public JwtClaimsBuilder withClaim(String claimName, String value) {
        jwtClaims.setClaim(claimName, value);
        return this;
    }

    public JwtClaims build() {
        jwtClaims.setIssuedAtToNow();
        jwtClaims.setGeneratedJwtId();
        return jwtClaims;
    }
}
