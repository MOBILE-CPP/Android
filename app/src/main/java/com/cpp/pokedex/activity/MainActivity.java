package com.cpp.pokedex.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cpp.pokedex.R;
import com.cpp.pokedex.adapter.PokeAdapter;
import com.cpp.pokedex.models.ImageData;
import com.cpp.pokedex.models.PokemonModel;
import com.cpp.pokedex.models.UserLogado;
import com.cpp.pokedex.pokeApi.RetrofitConfig;
import com.google.gson.JsonObject;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextView userName;
    TextView qtd;
    UserLogado userLogado;

    private RecyclerView recyclerT;
    private RecyclerView recyclerH;
    private PokeAdapter pokeAdapter;
    private List<PokemonModel> listaHab = new ArrayList<>();
    private List<PokemonModel> listaTipo = new ArrayList<>();
    private List<PokemonModel> listaTotal = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b = getIntent().getExtras();
        String nome = b.getString("nome");
        userLogado = new UserLogado();
        userLogado.setLogin(nome);
        if(b.getString("id") != null){
            userLogado.setId(b.getString("id"));
        }
        setContentView(R.layout.activity_main);
        userName = findViewById(R.id.textViewUserDash);
        userName.setText(userLogado.getLogin());
        qtd = findViewById(R.id.textViewQuantidade);
        recyclerT = findViewById(R.id.listViewTipoDashboard);
        recyclerH = findViewById(R.id.listViewHabilityDashboard);

        listaTotal = getAllPikemons();

        List<ImageData> images = getAllImages();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_pokedex, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.novoPokemon:

                novoPokemon();
                return true;

            case R.id.listarPokemon:

                listarPokemon();

                return true;

            case R.id.tipoPokemon:

                tipoPokemon();

                return true;

            case R.id.habilidadPokemon:

                habilidadPokemon();

                return true;

            case R.id.btnLogout:

                Intent i = new Intent(MainActivity.this,LoginActivity.class);
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
        Intent intent = new Intent(MainActivity.this,NewpokActivity.class);
        startActivity(intent);
        finish();
    }

    public void listarPokemon(){
        Intent intent = new Intent(MainActivity.this,ListActivity.class);
        startActivity(intent);
        finish();
    }

    public void tipoPokemon(){
        Intent intent = new Intent(MainActivity.this,TypeActivity.class);
        startActivity(intent);
        finish();
    }

    public void habilidadPokemon(){
        Intent intent = new Intent(MainActivity.this,HabilityActivity.class);
        startActivity(intent);
        finish();
    }

    public void updateReciclerHabilide(){
        PokemonModel pokemon = new PokemonModel();
        pokemon.setName("Charizard");
        pokemon.setType("Fogo");
        List<String> habilitis = new ArrayList<>();
        habilitis.add("Placaje");
        habilitis.add("Lanzallamas");
        habilitis.add("Ataque Ala");
        habilitis.add("Ascuas");
        pokemon.setSkills(habilitis);
        ImageData foto = new ImageData();
        pokemon.setImageData(foto);
        pokemon.setUsername("martin");
        listaHab.add(pokemon);
        listaTotal.add(pokemon);

        pokeAdapter = new PokeAdapter(listaHab);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerH.setLayoutManager(layoutManager);
        recyclerH.setHasFixedSize(true);
        recyclerH.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        recyclerH.setAdapter(pokeAdapter);
    }

    public void updateReciclerTipo(){
        PokemonModel pokemon = new PokemonModel();
        pokemon.setName("Pikachu");
        pokemon.setType("Eletrico");
        List<String> habilitis = new ArrayList<>();
        habilitis.add("Placaje");
        habilitis.add("Trueno");
        habilitis.add("Raio");
        habilitis.add("Encanto");
        pokemon.setSkills(habilitis);
        ImageData foto = new ImageData();
        pokemon.setImageData(foto);
        pokemon.setUsername("martin");

        listaTipo.add(pokemon);
        listaTotal.add(pokemon);
        pokeAdapter = new PokeAdapter(listaTipo);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerT.setLayoutManager(layoutManager);
        recyclerT.setHasFixedSize(true);
        recyclerT.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        recyclerT.setAdapter(pokeAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateReciclerHabilide();
        updateReciclerTipo();
        qtd.setText(" "+listaTotal.size());
    }

    public List<PokemonModel> getAllPikemons(){
        List<PokemonModel> list = new ArrayList<>();
        Call<JsonObject> getAllPkm = new RetrofitConfig().getPokeService().getAllPokemons();
        getAllPkm.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful()){
                    System.out.println(response.body().get("content"));
//                    transformar o content en array de pokemons
                }else{
                    String msg = "";
                    try {
                        msg = response.errorBody().string();
                        msg = msg.substring(12,msg.length()-2);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
        return list;
    }

    public List<ImageData> getAllImages(){
        List<ImageData> list = new ArrayList<>();
        Call<JsonObject> getAllImg = new RetrofitConfig().getPokeService().getAllImages();
        getAllImg.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful()){
                    System.out.println(response.body().get("content"));
//                    Asinar a imagem ao pekemon segundo o id
                }else{
                    String msg = "";
                    try {
                        msg = response.errorBody().string();
                        msg = msg.substring(12,msg.length()-2);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
        return list;
    }
}