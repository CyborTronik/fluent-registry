package com.github.cybortronik.registry.bean;

/**
 * Created by stanislav on 10/28/15.
 */
public class Company extends Bean {
    private String name;
    private String logoPath;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
