package org.beatrice.dgtuProject.dto;


import java.util.Objects;

public final class UserRequest {
    private final String name;
    private final String email;
    private final String city;
    private final String password;

    public UserRequest(String name, String email, String city, String password) {
        this.name = name;
        this.email = email;
        this.city = city;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getCity() {
        return city;
    }

    public String getPassword() {
        return password;
    }
}
