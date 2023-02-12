package com.cpp.pokedex.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.cpp.pokedex.R;

public class Splash extends AppCompatActivity {

    ImageView pokeball;
    AnimatedVectorDrawable drawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        pokeball = findViewById(R.id.pokeball);
        drawable = (AnimatedVectorDrawable) pokeball.getDrawable();
        drawable.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Splash.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }

}