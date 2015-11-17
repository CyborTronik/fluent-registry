package com.github.cybortronik.registry.jwt;

import com.fatboyindustrial.gsonjodatime.Converters;
import com.github.cybortronik.registry.bean.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.NumericDate;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by stanislav on 11/17/15.
 */
public class JwtClaimsAdapter extends org.jose4j.jwt.JwtClaims {
    private JwtClaims jwtClaims;

    public JwtClaimsAdapter(JwtClaims jwtClaims) {
        this.jwtClaims = jwtClaims;
    }

    @Override
    public String getIssuer() throws MalformedClaimException {
        return jwtClaims.getIssuer();
    }

    @Override
    public String getSubject() throws MalformedClaimException {
        return jwtClaims.getSubject();
    }

    @Override
    public List<String> getAudience() throws MalformedClaimException {
        return jwtClaims.getAudience();
    }

    @Override
    public NumericDate getExpirationTime() throws MalformedClaimException {
        return jwtClaims.getExpirationTime();
    }

    @Override
    public NumericDate getNotBefore() throws MalformedClaimException {
        return jwtClaims.getNotBefore();
    }

    @Override
    public NumericDate getIssuedAt() throws MalformedClaimException {
        return jwtClaims.getIssuedAt();
    }

    @Override
    public String getJwtId() throws MalformedClaimException {
        return jwtClaims.getJwtId();
    }

    @Override
    public <T> T getClaimValue(String claimName, Class<T> type) throws MalformedClaimException {
        return jwtClaims.getClaimValue(claimName, type);
    }

    @Override
    public Object getClaimValue(String claimName) {
        return jwtClaims.getClaimValue(claimName);
    }

    @Override
    public NumericDate getNumericDateClaimValue(String claimName) throws MalformedClaimException {
        return jwtClaims.getNumericDateClaimValue(claimName);
    }

    @Override
    public String getStringClaimValue(String claimName) throws MalformedClaimException {
        return jwtClaims.getStringClaimValue(claimName);
    }

    @Override
    public List<String> getStringListClaimValue(String claimName) throws MalformedClaimException {
        return jwtClaims.getStringListClaimValue(claimName);
    }

    @Override
    public boolean isClaimValueOfType(String claimName, Class type) {
        return jwtClaims.isClaimValueOfType(claimName, type);
    }

    @Override
    public boolean isClaimValueString(String claimName) {
        return jwtClaims.isClaimValueString(claimName);
    }

    @Override
    public boolean isClaimValueStringList(String claimName) {
        return jwtClaims.isClaimValueStringList(claimName);
    }

    @Override
    public Map<String, List<Object>> flattenClaims() {
        return jwtClaims.flattenClaims();
    }

    @Override
    public Map<String, List<Object>> flattenClaims(Set<String> omittedClaims) {
        return jwtClaims.flattenClaims(omittedClaims);
    }

    @Override
    public Map<String, Object> getClaimsMap() {
        return jwtClaims.getClaimsMap();
    }

    @Override
    public Map<String, Object> getClaimsMap(Set<String> omittedClaims) {
        return jwtClaims.getClaimsMap(omittedClaims);
    }

    @Override
    public Collection<String> getClaimNames(Set<String> omittedClaims) {
        return jwtClaims.getClaimNames(omittedClaims);
    }

    @Override
    public Collection<String> getClaimNames() {
        return jwtClaims.getClaimNames();
    }

    @Override
    public String getRawJson() {
        return jwtClaims.getRawJson();
    }

    public User readUser() {
        String userJson = jwtClaims.getClaimValue("user").toString();
        Gson gson = buildGson();
        return gson.fromJson(userJson, User.class);
    }

    private Gson buildGson() {
        return Converters
                .registerDateMidnight(Converters
                        .registerLocalTime(Converters
                                .registerDateTime(new GsonBuilder()))).create();
    }
}
