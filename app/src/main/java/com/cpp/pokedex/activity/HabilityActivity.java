package com.cpp.pokedex.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.cpp.pokedex.R;
import com.cpp.pokedex.adapter.PokeAdapter;
import com.cpp.pokedex.models.PokemonModel;
import com.cpp.pokedex.pokeApi.RetrofitConfig;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HabilityActivity extends AppCompatActivity {

    private RecyclerView recyclerViewPoke;
    private List<PokemonModel> list = new ArrayList<>();
    private EditText skill;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hability);
    }
    public void search(View view){
        skill = findViewById(R.id.skill);
        String hab = skill.getText().toString();
        System.out.println(hab);
        Call<List<PokemonModel>> call = new RetrofitConfig().getPokeService().findBySkill(hab);
        call.enqueue(new Callback<List<PokemonModel>>() {
            @Override
            public void onResponse(Call<List<PokemonModel>> call, Response<List<PokemonModel>> response) {
                if(response.isSuccessful()){
                    list = response.body();

                    PokeAdapter adapter = new PokeAdapter(list);
                    recyclerViewPoke = findViewById(R.id.recyclerViewPoke);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerViewPoke.setLayoutManager(layoutManager);
                    recyclerViewPoke.setHasFixedSize(true);
                    recyclerViewPoke.setAdapter(adapter);
                }else{
                    Toast.makeText(HabilityActivity.this, "Erro ao listar os pokémons " + response.errorBody(), Toast.LENGTH_SHORT).show();
                    //Intent intent = new Intent(HabilityActivity.this,MainActivity.class);
                    //startActivity(intent);
                    //finish();
                }
            }

            @Override
            public void onFailure(Call<List<PokemonModel>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}