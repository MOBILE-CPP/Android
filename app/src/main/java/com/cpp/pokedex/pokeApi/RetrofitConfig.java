package com.cpp.pokedex.pokeApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig {

    private final Retrofit retrofit;


    public RetrofitConfig() {
        this.retrofit = new Retrofit.Builder().baseUrl("http://10.0.2.2:5010/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public PokeService getPokeService(){
        return this.retrofit.create(PokeService.class);
    }
}
