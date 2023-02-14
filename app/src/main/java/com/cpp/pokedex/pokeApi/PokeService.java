package com.cpp.pokedex.pokeApi;

import com.cpp.pokedex.models.AuthModel;
import com.cpp.pokedex.models.ImageData;
import com.cpp.pokedex.models.PokemonModel;
import com.cpp.pokedex.models.UserLogado;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface PokeService {

    @GET("auth")
    Call<JsonObject> getFullUsers();

    @POST("auth")
    Call<UserLogado> addUser(@Body AuthModel user);

    @POST("auth/login")
    Call<JsonObject> logar(@Body AuthModel user);

    @GET("pokemon")
    Call<List<PokemonModel>> getAllPokemons();

    @POST("pokemon")
    Call<JsonObject> addPokemon(@Body PokemonModel pokemon);

    @Multipart
    @POST("image")
    Call<JsonObject> addImage(@Part ImageData image);

    @GET("image")
    Call<JsonObject> getAllImages();   
}
