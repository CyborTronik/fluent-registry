package com.github.cybortronik.registry.bean;

import com.google.gson.JsonElement;

import java.util.List;

/**
 * Created by stanislav on 11/17/15.
 */
public class UserRequest {

    private String displayName;
    private String email;
    private String password;
    private String passwordConfirmation;
    private JsonElement details;
    private List<String> roles;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public JsonElement getDetails() {
        return details;
    }

    public void setDetails(JsonElement details) {
        this.details = details;
    }
}
