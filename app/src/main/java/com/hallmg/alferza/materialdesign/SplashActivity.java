package com.hallmg.alferza.materialdesign;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //Creamos un hilo con un tiempo de vida
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Cargamos la siguiente pantalla una vez que el splash muera
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();//Cerramos este hilo
            }
        }, 4000);//Asignamos un tiempo de vida en la pantalla
    }
}
