package com.cpp.pokedex.pokeApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig {

    private final Retrofit retrofit;


    public RetrofitConfig() {
        this.retrofit = new Retrofit.Builder().baseUrl("http://127.0.0.1:5010/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public PokeService getPokeService(){
        return this.retrofit.create(PokeService.class);
    }
}
