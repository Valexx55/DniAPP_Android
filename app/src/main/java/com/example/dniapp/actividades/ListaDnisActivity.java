package com.example.dniapp.actividades;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.dniapp.R;
import com.example.dniapp.adapter.DniAdapter;
import com.example.dniapp.beans.Dni;
import com.example.dniapp.persistence.BaseDatosDni;
import com.example.dniapp.util.ComparadorDni;
import com.example.dniapp.persistence.Preferencias;

import java.util.Collections;
import java.util.List;

public class ListaDnisActivity extends AppCompatActivity {

    private List<Dni> lista_dnis;
    private RecyclerView recyclerView;
    private DniAdapter dniAdapter;
    private Toast mensaje_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_dnis);

        //this.lista_dnis = Preferencias.cargarFicheroDni(this);

        BaseDatosDni baseDatosDni = new BaseDatosDni(this, BaseDatosDni.NOMBRE_BD, null, BaseDatosDni.VERSION_BD);
        this.lista_dnis = baseDatosDni.buscarDnis();

        if (lista_dnis != null && lista_dnis.size() > 0) {
            findViewById(R.id.caja_no_resultado).setVisibility(View.GONE);
            dniAdapter = new DniAdapter(lista_dnis);
            this.recyclerView = findViewById(R.id.recycler_view);
            recyclerView.setAdapter(dniAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        }

    }

    public void ordenarPorDni (View view)
    {
        Collections.sort(lista_dnis);
        dniAdapter.setDniList(lista_dnis);
        dniAdapter.notifyDataSetChanged();
        mensaje_info =  Toast.makeText(this, "ORDENADO POR NÚM", Toast.LENGTH_SHORT);
        mensaje_info.show();
    }

    public void ordenarPorLetra (View view)
    {
        Collections.sort(lista_dnis, new ComparadorDni());
        dniAdapter.setDniList(lista_dnis);
        dniAdapter.notifyDataSetChanged();

        mensaje_info =  Toast.makeText(this, "ORDENADO POR LETRA", Toast.LENGTH_SHORT);
        mensaje_info.show();

    }
}
