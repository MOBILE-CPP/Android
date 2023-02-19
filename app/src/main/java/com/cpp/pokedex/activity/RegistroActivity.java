package com.cpp.pokedex.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.cpp.pokedex.R;
import com.cpp.pokedex.models.ApiResponse;
import com.cpp.pokedex.models.AuthModel;
import com.cpp.pokedex.models.UserLogado;
import com.cpp.pokedex.pokeApi.RetrofitConfig;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroActivity extends AppCompatActivity {

    EditText usuario;
    EditText senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        usuario = findViewById(R.id.editTextTextUserReg);
        senha = findViewById(R.id.editTextPasswordReg);
    }

    public void registrar(View view){
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Guardando Usuario...");
        progressDialog.show();

        AuthModel authUser = new AuthModel();
        authUser.setLogin(usuario.getText().toString());
        authUser.setPassword(senha.getText().toString());

        Call<ApiResponse> resp = new RetrofitConfig().getPokeService().addUser(authUser);
        resp.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                ApiResponse r = response.body();
                if(response.isSuccessful()){
                    progressDialog.dismiss();
                    Toast.makeText(RegistroActivity.this, r.getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegistroActivity.this,MainActivity.class);
                    Bundle bundle = new Bundle();
                    Gson gson = new Gson();
                    String json = gson.toJson(r.getData());
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(json);
                        bundle.putString("nome",jsonObject.getString("login"));
                        bundle.putString("id",jsonObject.getString("id"));
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(RegistroActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }else{
                    progressDialog.dismiss();
                    try {
                        String er = response.errorBody().string();
                        JSONObject jsonObject = new JSONObject(er);
                        Toast.makeText(RegistroActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {

            }
        });


    }

    public void backLogin(View view){
      Intent i = new Intent(RegistroActivity.this,LoginActivity.class);
      startActivity(i);
      finish();
    }
}