package com.cpp.pokedex;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.cpp.pokedex.models.AuthModel;
import com.cpp.pokedex.pokeApi.PokeService;
import com.cpp.pokedex.pokeApi.RetrofitConfig;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText user;
    EditText pass;
    PokeService pokeService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user = findViewById(R.id.editTextTextUser);
        pass = findViewById(R.id.editTextPassword);
    }

    public void logar(View view){
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Iniciando Sessão...");
        progressDialog.show();

        AuthModel authUser = new AuthModel();
        authUser.setLogin(user.getText().toString());
        authUser.setPassword(pass.getText().toString());


        Call<String> call = new RetrofitConfig().getPokeService().logar(authUser);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    progressDialog.dismiss();
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
            }
        });
}

    public void registro(View view){
        Intent intent = new Intent(LoginActivity.this,RegistroActivity.class);
        startActivity(intent);
        finish();
    }

}