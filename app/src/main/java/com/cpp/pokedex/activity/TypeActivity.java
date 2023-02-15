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
import com.cpp.pokedex.adapter.FilterAdapter;
import com.cpp.pokedex.models.Nomes;
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
    private List<PokemonModel> list = new ArrayList<>();
    private EditText type;
    private UserLogado userLogado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type);
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
        type = findViewById(R.id.type);
        String tipo = type.getText().toString();
        System.out.println(tipo);
        Call<Nomes> call = new RetrofitConfig().getPokeService().findByType(tipo);
        call.enqueue(new Callback<Nomes>() {
            @Override
            public void onResponse(Call<Nomes> call, Response<Nomes> response) {
                if(response.isSuccessful()){
                    Nomes list = new Nomes(response.body().getNomes());
                    FilterAdapter adapter = new FilterAdapter(list);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerViewPoke.setLayoutManager(layoutManager);
                    recyclerViewPoke.setHasFixedSize(true);
                    recyclerViewPoke.setAdapter(adapter);
                }else{
                    Toast.makeText(TypeActivity.this, "Habilidade não encontrada!", Toast.LENGTH_SHORT).show();
                    //Intent intent = new Intent(HabilityActivity.this,MainActivity.class);
                    //startActivity(intent);
                    //finish();
                }
            }

            @Override
            public void onFailure(Call<Nomes> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    public void voltar(View view){
        Intent intent = new Intent(TypeActivity.this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("nome",userLogado.getLogin());
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }
}