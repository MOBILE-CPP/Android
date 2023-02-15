package com.cpp.pokedex.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cpp.pokedex.R;
import com.cpp.pokedex.adapter.PokeAdapter;
import com.cpp.pokedex.adapter.PokeAdapterDashboard;
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
    private PokeAdapterDashboard pokeAdapterT;
    private PokeAdapterDashboard pokeAdapterH;
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

        Call<List<PokemonModel>> getAllPkm = new RetrofitConfig().getPokeService().getAllPokemons();

        getAllPkm.enqueue(new Callback<List<PokemonModel>>() {
            @Override
            public void onResponse(Call<List<PokemonModel>> call, Response<List<PokemonModel>> response) {
                System.out.println(response.body());
                if(response.isSuccessful()){
                    listaTotal = response.body();
                    qtd = findViewById(R.id.textViewQuantidade);
                    recyclerT = findViewById(R.id.listViewTipoDashboard);
                    recyclerH = findViewById(R.id.listViewHabilityDashboard);
                    String qt = String.valueOf(listaTotal.size());
                    qtd.setText(qt);
                    pokeAdapterT = new PokeAdapterDashboard(listaTotal);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerT.setLayoutManager(layoutManager);
                    recyclerT.setHasFixedSize(true);
                    recyclerT.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
                    recyclerT.setAdapter(pokeAdapterT);

                    pokeAdapterH = new PokeAdapterDashboard(listaTotal);
                    RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(getApplicationContext());
                    recyclerH.setLayoutManager(layoutManager2);
                    recyclerH.setHasFixedSize(true);
                    recyclerH.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
                    recyclerH.setAdapter(pokeAdapterH);
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
            public void onFailure(Call<List<PokemonModel>> call, Throwable t) {

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
        Bundle bundle = new Bundle();
        bundle.putString("nome",userLogado.getLogin());
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    public void listarPokemon(){
        Intent intent = new Intent(MainActivity.this,ListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("nome",userLogado.getLogin());
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    public void tipoPokemon(){
        Intent intent = new Intent(MainActivity.this,TypeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("nome",userLogado.getLogin());
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    public void habilidadPokemon(){
        Intent intent = new Intent(MainActivity.this,HabilityActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("nome",userLogado.getLogin());
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

}