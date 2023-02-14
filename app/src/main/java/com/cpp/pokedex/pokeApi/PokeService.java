package com.cpp.pokedex.pokeApi;

import com.cpp.pokedex.models.AuthModel;
import com.cpp.pokedex.models.ImageData;
import com.cpp.pokedex.models.PokemonModel;
import com.cpp.pokedex.models.UserLogado;
import com.google.gson.JsonObject;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

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
    Call<Integer> addImage(@Part MultipartBody.Part filePart);

    @GET("pokemon/search/type")
    Call<List<PokemonModel>> findByType(@Query("type") String type);

    @GET("image")
    Call<JsonObject> getAllImages();

    @GET("pokemon/search/skill/{skill}")
    Call<List<PokemonModel>> findBySkill(@Path("skill") String skill);
}
