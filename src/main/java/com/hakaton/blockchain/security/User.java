package com.hakaton.blockchain.security;

import lombok.Data;

import java.util.List;

@Data
public class User {

    private String login;
    private String password;
    private List<String> roles;

}
