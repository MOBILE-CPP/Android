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
import com.cpp.pokedex.adapter.FilterAdapter;
import com.cpp.pokedex.adapter.PokeAdapter;
import com.cpp.pokedex.models.Nomes;
import com.cpp.pokedex.models.PokemonModel;
import com.cpp.pokedex.models.UserLogado;
import com.cpp.pokedex.pokeApi.RetrofitConfig;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HabilityActivity extends AppCompatActivity {
    private RecyclerView recyclerViewPoke;
    private PokeAdapter adapter;
    private List<PokemonModel> list = new ArrayList<>();
    private EditText skill;
    private UserLogado userLogado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hability);
        recyclerViewPoke = findViewById(R.id.recyclerViewPokeType);
        Bundle b = getIntent().getExtras();
        String nome = b.getString("nome");
        userLogado = new UserLogado();
        userLogado.setLogin(nome);
        if(b.getString("id") != null){
            userLogado.setId(b.getString("id"));
        }
    }
    public void search(View view){
        skill = findViewById(R.id.editTextHability);
        String hab = skill.getText().toString();
        System.out.println(hab);

        Call<List<PokemonModel>> listar = new RetrofitConfig().getPokeService().getAllPokemons();
        listar.enqueue(new Callback<List<PokemonModel>>() {
            @Override
            public void onResponse(Call<List<PokemonModel>> call, Response<List<PokemonModel>> response) {
                skill = findViewById(R.id.editTextHability);
                String habil = skill.getText().toString();
                if(response.isSuccessful()){
                    List<PokemonModel> lista = response.body();
                    List<PokemonModel> listaHabil = new ArrayList<>();
                    for(PokemonModel p : lista){
                        for(String s : p.getSkills()){
                            if(s.equalsIgnoreCase(habil)){
                                listaHabil.add(p);
                            }
                        }
                    }
                    if(listaHabil.size() == 0){
                        Toast.makeText(HabilityActivity.this, "Nenhum Pokémon com essa habilidade!", Toast.LENGTH_SHORT).show();
                    }
                    recyclerViewPoke = findViewById(R.id.recyclerViewPokeType);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerViewPoke.setLayoutManager(layoutManager);
                    recyclerViewPoke.setHasFixedSize(true);
                    recyclerViewPoke.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
                    adapter = new PokeAdapter(listaHabil);
                    recyclerViewPoke.setAdapter(adapter);
                }else{
                    Toast.makeText(HabilityActivity.this, "Erro ao tentar pesquisar", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<PokemonModel>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(HabilityActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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
                Intent intent = new Intent(HabilityActivity.this, MainActivity.class);
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

                tipoPokemon();
                return true;

            case R.id.habilidadPokemon:

                return true;

            case R.id.btnLogout:

                Intent i = new Intent(HabilityActivity.this,LoginActivity.class);
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
        Intent intent = new Intent(HabilityActivity.this,ListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("nome",userLogado.getLogin());
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    public void novoPokemon(){
        Intent intent = new Intent(HabilityActivity.this,NewpokActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("nome",userLogado.getLogin());
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    public void tipoPokemon(){
        Intent intent = new Intent(HabilityActivity.this,TypeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("nome",userLogado.getLogin());
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }
}