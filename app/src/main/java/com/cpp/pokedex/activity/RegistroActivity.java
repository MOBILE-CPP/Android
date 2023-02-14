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
import com.cpp.pokedex.models.UserLogado;
import com.cpp.pokedex.pokeApi.RetrofitConfig;

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

        Call<UserLogado> call = new RetrofitConfig().getPokeService().addUser(authUser);
       call.enqueue(new Callback<UserLogado>() {
           @Override
           public void onResponse(Call<UserLogado> call, Response<UserLogado> response) {
               if(response.isSuccessful()){
                   progressDialog.dismiss();
                   Intent intent = new Intent(RegistroActivity.this,MainActivity.class);
                   Bundle bundle = new Bundle();
                   bundle.putString("nome",authUser.getLogin());
                   bundle.putString("id",response.body().getId());
                   intent.putExtras(bundle);
                   startActivity(intent);
                   finish();
               }else{

                   progressDialog.dismiss();
                   Toast.makeText(RegistroActivity.this, response.errorBody().source().toString().substring(6,
                           response.errorBody().source().toString().length()-1), Toast.LENGTH_SHORT).show();
               }
           }

           @Override
           public void onFailure(Call<UserLogado> call, Throwable t) {
                t.printStackTrace();
           }
       });
    }

    public void backLogin(View view){
      Intent i = new Intent(RegistroActivity.this,LoginActivity.class);
      startActivity(i);
      finish();
    }
}