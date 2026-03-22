package com.thailandcube.models;

import java.util.Collections;
import java.util.List;

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
    private List<Role> roles;

    public Person(String name, int wcaUserId, String wcaId, int registrantId, String countryIso2, String gender, Registration registration, Avatar avatar, List<Role> roles) {
        this.name = name;
        this.wcaUserId = wcaUserId;
        this.wcaId = wcaId;
        this.registrantId = registrantId;
        this.countryIso2 = countryIso2;
        this.gender = gender;
        this.registration = registration;
        this.avatar = avatar;
        this.roles = roles;
    }

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

    public List<Role> getRoles() {
        return Collections.unmodifiableList(roles);
    }
}
