package com.cpp.pokedex.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cpp.pokedex.R;
import com.cpp.pokedex.adapter.FilterAdapter;
import com.cpp.pokedex.adapter.PokeAdapter;
import com.cpp.pokedex.models.ApiResponse;
import com.cpp.pokedex.models.PokemonModel;
import com.cpp.pokedex.models.UserLogado;
import com.cpp.pokedex.pokeApi.RetrofitConfig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextView userName;
    TextView qtd;
    UserLogado userLogado;

    TextView tipoTop1;
    TextView tipoTop2;
    TextView tipoTop3;

    TextView habTop1;
    TextView habTop2;
    TextView habTop3;

    private RecyclerView recyclerH;
    private PokeAdapter pokeAdapterH;
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
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<PokemonModel>> call, Response<List<PokemonModel>> response) {
                if(response.isSuccessful()){
                    listaTotal = response.body();
                    qtd = findViewById(R.id.textViewQuantidade);
                    recyclerH = findViewById(R.id.reciclerViewHome);
                    String qt = String.valueOf(listaTotal.size());
                    qtd.setText(qt);



                    Map<String, Integer> typeCounts = new HashMap<>();
                    Map<String, Integer> abilityCounts = new HashMap<>();

                    for (PokemonModel p : listaTotal) {
                        // Count the types and add to the typeCounts map
                        String type = p.getType();
                        if (typeCounts.containsKey(type)) {
                            typeCounts.put(type, typeCounts.get(type) + 1);
                        } else {
                            typeCounts.put(type, 1);
                        }

                        for (String ability : p.getSkills()) {
                            if (abilityCounts.containsKey(ability)) {
                                abilityCounts.put(ability, abilityCounts.get(ability) + 1);
                            } else {
                                abilityCounts.put(ability, 1);
                            }
                        }
                    }

                    List<Map.Entry<String, Integer>> typeEntries = new ArrayList<>(typeCounts.entrySet());
                    Collections.sort(typeEntries, new Comparator<Map.Entry<String, Integer>>() {
                        @Override
                        public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) {
                            return e2.getValue().compareTo(e1.getValue());
                        }
                    });
                    List<String> topTypes = new ArrayList<>();
                    int countType1 = 0;
                    int countType2 = 0;
                    int countType3 = 0;
                    for (int i = 0; i < 3 && i < typeEntries.size(); i++) {
                        String type = typeEntries.get(i).getKey();
                        topTypes.add(type);
                        int count = typeEntries.get(i).getValue();
                        if (i == 0) {
                            countType1 = count;
                        } else if (i == 1) {
                            countType2 = count;
                        } else if (i == 2) {
                            countType3 = count;
                        }
                    }

                    List<Map.Entry<String, Integer>> abilityEntries = new ArrayList<>(abilityCounts.entrySet());
                    Collections.sort(abilityEntries, new Comparator<Map.Entry<String, Integer>>() {
                        @Override
                        public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) {
                            return e2.getValue().compareTo(e1.getValue());
                        }
                    });
                    List<String> topAbilities = new ArrayList<>();
                    int countAbility1 = 0;
                    int countAbility2 = 0;
                    int countAbility3 = 0;
                    for (int i = 0; i < 3 && i < abilityEntries.size(); i++) {
                        String ability = abilityEntries.get(i).getKey();
                        topAbilities.add(ability);
                        int count = abilityEntries.get(i).getValue();
                        if (i == 0) {
                            countAbility1 = count;
                        } else if (i == 1) {
                            countAbility2 = count;
                        } else if (i == 2) {
                            countAbility3 = count;
                        }
                    }
                    tipoTop1 = findViewById(R.id.textViewTipotop1);
                    tipoTop2 = findViewById(R.id.textViewTipotop2);
                    tipoTop3 = findViewById(R.id.textViewTipotop3);

                    habTop1 = findViewById(R.id.textViewHabitop1);
                    habTop2 = findViewById(R.id.textViewHabitop2);
                    habTop3 = findViewById(R.id.textViewHabitop3);
                    tipoTop1.setText(topTypes.get(0) +" - "+countType1);
                    tipoTop2.setText(topTypes.get(1) +" - "+countType2);
                    tipoTop3.setText(topTypes.get(2) +" - "+countType3);

                    habTop1.setText(topAbilities.get(0) +" - "+countAbility1);
                    habTop2.setText(topAbilities.get(1) +" - "+countAbility2);
                    habTop3.setText(topAbilities.get(2) +" - "+countAbility3);
                    recyclerH = findViewById(R.id.reciclerViewHome);
                    pokeAdapterH = new PokeAdapter(listaTotal);
                    RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(getApplicationContext());
                    recyclerH.setLayoutManager(layoutManager2);
                    recyclerH.setHasFixedSize(true);
                    recyclerH.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
                    recyclerH.setAdapter(pokeAdapterH);
                }else{
                    try {
                        Toast.makeText(MainActivity.this, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

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