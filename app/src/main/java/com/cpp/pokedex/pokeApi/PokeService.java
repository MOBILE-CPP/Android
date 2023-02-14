package com.cpp.pokedex.pokeApi;

import com.cpp.pokedex.models.AuthModel;
import com.cpp.pokedex.models.PokemonModel;
import com.cpp.pokedex.models.UserLogado;
import com.google.gson.JsonObject;

import org.w3c.dom.Text;

import java.util.List;

import okhttp3.ResponseBody;
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
    Call<JsonObject> logar(@Body AuthModel user);

    @GET("pokemon")
    Call<List<PokemonModel>> getAllPokemons();
}
