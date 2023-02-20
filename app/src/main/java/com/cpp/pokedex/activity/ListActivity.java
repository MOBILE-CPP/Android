package com.cpp.pokedex.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.cpp.pokedex.R;
import com.cpp.pokedex.adapter.PokeAdapter;
import com.cpp.pokedex.models.ImageData;
import com.cpp.pokedex.models.PokemonModel;
import com.cpp.pokedex.models.UserLogado;
import com.cpp.pokedex.pokeApi.RetrofitConfig;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListActivity extends AppCompatActivity {

    private RecyclerView recyclerViewPoke;
    private List<PokemonModel> list = new ArrayList<>();
    private UserLogado userLogado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Bundle b = getIntent().getExtras();
        if(b != null){
            String nome = b.getString("nome");
            userLogado = new UserLogado();
            userLogado.setLogin(nome);
        }
        if(b.getString("id") != null){
            userLogado.setId(b.getString("id"));
        }

        recyclerViewPoke = findViewById(R.id.recyclerViewPoke);
        Call<List<PokemonModel>> call = new RetrofitConfig().getPokeService().getAllPokemons();
        call.enqueue(new Callback<List<PokemonModel>>() {
            @Override
            public void onResponse(Call<List<PokemonModel>> call, Response<List<PokemonModel>> response) {
                if(response.isSuccessful()){
                    list = response.body();
                    PokeAdapter adapter = new PokeAdapter(list);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerViewPoke.setLayoutManager(layoutManager);
                    recyclerViewPoke.setHasFixedSize(true);
                    recyclerViewPoke.setAdapter(adapter);
                }else{
                    Toast.makeText(ListActivity.this, "Erro ao listar todos os pokémons " + response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ListActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<List<PokemonModel>> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_pokedex, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.home:
                Intent intent = new Intent(ListActivity.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("nome",userLogado.getLogin());
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
                return true;

            case R.id.novoPokemon:

                novoPokemon();
                return true;

            case R.id.listarPokemon:

                return true;

            case R.id.tipoPokemon:

                tipoPokemon();

                return true;

            case R.id.habilidadPokemon:

                habilidadPokemon();

                return true;

            case R.id.btnLogout:

                Intent i = new Intent(ListActivity.this,LoginActivity.class);
                startActivity(i);
                finish();

                return true;
            case R.id.bottonSair:

                finishAndRemoveTask();

                return true;

            default:

                return super.onOptionsItemSelected(item);
        }

    }

    public void novoPokemon(){
        Intent intent = new Intent(ListActivity.this,NewpokActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("nome",userLogado.getLogin());
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }


    public void tipoPokemon(){
        Intent intent = new Intent(ListActivity.this,TypeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("nome",userLogado.getLogin());
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    public void habilidadPokemon(){
        Intent intent = new Intent(ListActivity.this,HabilityActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("nome",userLogado.getLogin());
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }
}