package com.cpp.pokedex.pokeApi;

import com.cpp.pokedex.models.AuthModel;
import com.cpp.pokedex.models.UserLogado;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface PokeService {

    @GET("auth")
    Call<JsonObject> getFullUsers();

    @POST("auth")
    Call<UserLogado> addUser(@Body AuthModel user);

    @POST("auth/login")
    Call<String> logar(@Body AuthModel user);

}
