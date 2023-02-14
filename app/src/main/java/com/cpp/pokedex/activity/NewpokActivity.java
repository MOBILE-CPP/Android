package com.cpp.pokedex.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import com.cpp.pokedex.R;

import androidx.annotation.Nullable;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import com.cpp.pokedex.models.PokemonModel;
import com.cpp.pokedex.models.ImageData;
import com.cpp.pokedex.pokeApi.RetrofitConfig;
import com.google.gson.JsonObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewpokActivity extends AppCompatActivity {

    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView listView;
    private Button button;
    private ImageButton imageButton;
    private ImageView imgGallery;
    private final int GALLERY_REQ_CODE = 1000;
    private EditText nome, tipo,username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newpok);

        listView = findViewById(R.id.listView);
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem(view);
            }
        });

        items = new ArrayList<>();
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(itemsAdapter);
        setUpListViewListener();

        imageButton = findViewById(R.id.imageButton);
        imgGallery = findViewById(R.id.imageView);

        imageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent gallery = new Intent(Intent.ACTION_PICK);
                gallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(gallery, GALLERY_REQ_CODE);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            if (requestCode == GALLERY_REQ_CODE){
                imgGallery.setImageURI(data.getData());
            }
        }
    }

    private void setUpListViewListener() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Context context = getApplicationContext();
                Toast.makeText(context, "Item Removido", Toast.LENGTH_LONG).show();
                items.remove(i);
                itemsAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    private void addItem(View view) {
        EditText input = findViewById(R.id.editTextHabilidades);
        String itemText = input.getText().toString();
        if (items.size()<3){
            if(!(itemText.equals(""))){
                itemsAdapter.add(itemText);
                input.setText("");
            } else {
                Toast.makeText(getApplicationContext(), "Favor inserir texto..", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Máximo de 3 habilidades..", Toast.LENGTH_LONG).show();
        }

    }
    public void cadastrar(View view) {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Salvando pokémon...");
        progressDialog.show();
        nome = findViewById(R.id.editTextName);
        tipo = findViewById(R.id.editTextTipo);
        username = findViewById(R.id.username);

            PokemonModel pokemon = new PokemonModel();
            pokemon.setName(nome.toString());
            List<String> skills = new ArrayList<String>(items);
            pokemon.setSkills(skills);
            pokemon.setType(tipo.toString());
            pokemon.setUsername(username.toString());
            ImageData image = new ImageData();
            Bitmap bitmap = ((BitmapDrawable) imgGallery.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageInByte = baos.toByteArray();
            image.setImageData(imageInByte);
            image.setName(nome.toString());
            image.setType(tipo.toString());
            image.setPokemonModel(pokemon);
            pokemon.setImageData(image);
            Call<JsonObject> call = new RetrofitConfig().getPokeService().addPokemon(pokemon);
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if(response.isSuccessful()){
                        progressDialog.dismiss();
                        Toast.makeText(NewpokActivity.this, "Pokémon cadastrado com sucesso!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(NewpokActivity.this,ListActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(NewpokActivity.this, response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        Call<JsonObject> callImage = new RetrofitConfig().getPokeService().addImage(image);
        callImage.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful()){
                    progressDialog.dismiss();
                    Toast.makeText(NewpokActivity.this, "Imagem cadastrada com sucesso!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(NewpokActivity.this,ListActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    progressDialog.dismiss();
                    Toast.makeText(NewpokActivity.this, response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                t.printStackTrace();
            }
        });
        }

}
