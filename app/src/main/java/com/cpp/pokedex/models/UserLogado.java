package com.cpp.pokedex.models;

import java.io.Serializable;

public class UserLogado implements Serializable {

    private String id;
    private String login;
    private String password;

    public UserLogado(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
