package com.cpp.pokedex.pokeApi;

import android.graphics.Bitmap;
import android.net.Uri;

import com.cpp.pokedex.models.ApiResponse;
import com.cpp.pokedex.models.AuthModel;
import com.cpp.pokedex.models.ImageData;
import com.cpp.pokedex.models.Nomes;
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
    Call<ApiResponse> getFullUsers();

    @POST("auth")
    Call<ApiResponse> addUser(@Body AuthModel user);

    @POST("auth/login")
    Call<ApiResponse> logar(@Body AuthModel user);

    @GET("pokemon")
    Call<List<PokemonModel>> getAllPokemons();

    @POST("pokemon")
    Call<JsonObject> addPokemon(@Body PokemonModel pokemon);

    @Multipart
    @POST("image")
    Call<Integer> addImage(@Part MultipartBody.Part filePart);

    @GET("pokemon/search/type")
    Call<Nomes> findByType(@Query("type") String type);

    @GET("pokemon/search/skill")
    Call<Nomes> findBySkill(@Query("skill") String skill);
}
