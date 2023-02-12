package com.cpp.pokedex.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.cpp.pokedex.R;
import com.cpp.pokedex.models.AuthModel;
import com.cpp.pokedex.pokeApi.PokeService;
import com.cpp.pokedex.pokeApi.RetrofitConfig;
import com.google.gson.JsonObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText user;
    EditText pass;
    PokeService pokeService;
    AuthModel authUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user = findViewById(R.id.editTextTextUser);
        pass = findViewById(R.id.editTextPassword);
        authUser = new AuthModel();
    }

    public void logar(View view){
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Iniciando Sessão...");
        progressDialog.show();

        authUser.setLogin(user.getText().toString());
        authUser.setPassword(pass.getText().toString());


        Call<JsonObject> call = new RetrofitConfig().getPokeService().logar(authUser);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful()){
                    progressDialog.dismiss();
                        String msg = String.valueOf(response.body().get("message"));
                        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("nome",authUser.getLogin());
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                }else{
                    progressDialog.dismiss();
                    try {
                        String msg = response.errorBody().string();
                        msg = msg.substring(12,msg.length()-2);
                        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        }

    public void registro(View view){
        Intent intent = new Intent(LoginActivity.this,RegistroActivity.class);
        startActivity(intent);
        finish();
    }

}