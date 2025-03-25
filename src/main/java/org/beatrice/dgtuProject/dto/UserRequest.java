package org.beatrice.dgtuProject.dto;

public class UserRequest {
    private String email;
    private String name;
    private String password;
    private String city;

    public UserRequest(String email, String name, String password, String city) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
