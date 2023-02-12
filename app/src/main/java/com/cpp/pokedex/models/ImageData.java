package com.cpp.pokedex.models;

import java.io.Serializable;

public class ImageData implements Serializable {
    private Long id;
    private String name;
    private String type;
    private byte[] imageData;
    private PokemonModel pokemonModel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public PokemonModel getPokemonModel() {
        return pokemonModel;
    }

    public void setPokemonModel(PokemonModel pokemonModel) {
        this.pokemonModel = pokemonModel;
    }
}
