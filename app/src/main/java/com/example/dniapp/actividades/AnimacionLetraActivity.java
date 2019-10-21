package com.example.dniapp.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.dniapp.R;
import com.example.dniapp.persistence.Preferencias;

public class AnimacionLetraActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animacion_letra);
        //Recuperar la letra y mostrarla
        char letra = getIntent().getCharExtra("LETRA", ' ');
        Log.d(MainActivity.TAG_APP, "Letra pasada = "+ letra);
        TextView caja_letra = findViewById(R.id.letra_resultado);
        caja_letra.setText(String.valueOf(letra));//pasamos el caracter a String para que no lo tome como un id de Res y falle
        //Hacer la animación
        Animation animacion = AnimationUtils.loadAnimation(this, R.anim.animacion_letra);
        caja_letra.startAnimation(animacion);
        //borrar el dni guardado
        Preferencias.guardarDNI(this, "");
    }
}
