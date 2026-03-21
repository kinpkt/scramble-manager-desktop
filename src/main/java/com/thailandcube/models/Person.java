package com.thailandcube.models;

import com.thailandcube.repositories.RoleList;

class Avatar {
    private String url;
    private String thumbUrl;

    public Avatar(String url, String thumbUrl) {
        this.url = url;
        this.thumbUrl = thumbUrl;
    }

    public String getUrl() {
        return url;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }
}

public class Person {
    private String name;
    private int wcaUserId;
    private String wcaId;
    private int registrantId;
    private String countryIso2;
    private String gender;
    private Registration registration;
    private Avatar avatar;
    private RoleList roles;

    public String getName() {
        return name;
    }

    public int getWcaUserId() {
        return wcaUserId;
    }

    public String getWcaId() {
        return wcaId;
    }

    public int getRegistrantId() {
        return registrantId;
    }

    public String getCountryIso2() {
        return countryIso2;
    }

    public String getGender() {
        return gender;
    }

    public Registration getRegistration() {
        return registration;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public RoleList getRoles() {
        return roles;
    }
}
