package com.hakaton.blockchain.security;

import java.util.List;

public class User {

    private String login;
    private String password;
    private List<String> roles;

    public User() {}

    public User(String login, String password, List<String> roles) {
        this.login = login;
        this.password = password;
        this.roles = roles;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public List<String> getRoles() {
        return roles;
    }

    public User setLogin(String login) {
        this.login = login;
        return this;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public User setRoles(List<String> roles) {
        this.roles = roles;
        return this;
    }
}
