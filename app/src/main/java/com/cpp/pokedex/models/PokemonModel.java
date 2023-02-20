package com.cpp.pokedex.models;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class PokemonModel implements Serializable {
    private String id;
    private String name;
    private String type;
    private List<String> skills;
    private int imageData;
    private String username;

    public PokemonModel(){

    }

    public PokemonModel(String name, String type, List<String> skills, int imageData, String username) {
        this.name = name;
        this.type = type;
        this.skills = skills;
        this.imageData = imageData;
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public int getImageData() {
        return imageData;
    }

    public void setImageData(int imageData) {
        this.imageData = imageData;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}