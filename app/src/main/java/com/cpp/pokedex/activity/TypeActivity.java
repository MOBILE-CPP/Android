package com.cpp.pokedex.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cpp.pokedex.R;
import com.cpp.pokedex.adapter.PokeAdapter;
import com.cpp.pokedex.models.PokemonModel;
import com.cpp.pokedex.models.UserLogado;
import com.cpp.pokedex.pokeApi.RetrofitConfig;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TypeActivity extends AppCompatActivity {

    private RecyclerView recyclerViewPoke;
    private PokeAdapter adapter;
    private List<PokemonModel> list = new ArrayList<>();
    private EditText type;
    private UserLogado userLogado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type);
        Bundle b = getIntent().getExtras();
        String nome = b.getString("nome");
        userLogado = new UserLogado();
        userLogado.setLogin(nome);
        if(b.getString("id") != null){
            userLogado.setId(b.getString("id"));
        }
    }
    public void search(View view){

        Call<List<PokemonModel>> listar = new RetrofitConfig().getPokeService().getAllPokemons();
        listar.enqueue(new Callback<List<PokemonModel>>() {
            @Override
            public void onResponse(Call<List<PokemonModel>> call, Response<List<PokemonModel>> response) {
                type = findViewById(R.id.editTextHability);
                String tipo = type.getText().toString();
                if(response.isSuccessful()){
                    List<PokemonModel> lista = response.body();
                    List<PokemonModel> listaTipo = new ArrayList<>();
                    for(PokemonModel p : lista){
                        if(p.getType().equalsIgnoreCase(tipo)){
                            listaTipo.add(p);
                        }
                    }
                    if(listaTipo.size() == 0){
                        Toast.makeText(TypeActivity.this, "Nenhum Pokémon do Tipo Pesquisado", Toast.LENGTH_SHORT).show();
                    }
                    recyclerViewPoke = findViewById(R.id.recyclerViewPokeType);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerViewPoke.setLayoutManager(layoutManager);
                    recyclerViewPoke.setHasFixedSize(true);
                    recyclerViewPoke.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
                    adapter = new PokeAdapter(listaTipo);
                    recyclerViewPoke.setAdapter(adapter);
                }else{
                    Toast.makeText(TypeActivity.this, "Erro ao tentar pesquisar", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<PokemonModel>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(TypeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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
                Intent intent = new Intent(TypeActivity.this, MainActivity.class);
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

                listarPokemon();

                return true;

            case R.id.tipoPokemon:


                return true;

            case R.id.habilidadPokemon:

                habilidadPokemon();

                return true;

            case R.id.btnLogout:

                Intent i = new Intent(TypeActivity.this,LoginActivity.class);
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


    public void listarPokemon(){
        Intent intent = new Intent(TypeActivity.this,ListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("nome",userLogado.getLogin());
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }


    public void habilidadPokemon(){
        Intent intent = new Intent(TypeActivity.this,HabilityActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("nome",userLogado.getLogin());
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    public void novoPokemon(){
        Intent intent = new Intent(TypeActivity.this,NewpokActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("nome",userLogado.getLogin());
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }
}