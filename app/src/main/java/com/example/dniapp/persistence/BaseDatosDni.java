package com.example.dniapp.persistence;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import androidx.annotation.Nullable;
import com.example.dniapp.beans.Dni;

import java.util.ArrayList;
import java.util.List;


public class BaseDatosDni extends SQLiteOpenHelper {

    public final static String NOMBRE_BD = "MiBDDni";
    public final static int VERSION_BD = 1;


    private static final String SQLCREATETABLADNI = "CREATE TABLE DNI (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "numero TEXT," +
            "letra CHAR)";

    public BaseDatosDni(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);//el método padre, llamará a Oncreate o OnUpgrade, segn corresponda
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQLCREATETABLADNI);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //En caso de que al constructor le pasemos un número de versión distinto a
        // la base de datos existente, este métdo es invocado. Esto sería necesario
        //cuando modificamos la estrucutura de la base de datos

        //Aquí, deberíamos
        // 1 - Extraer los datos de la vieja versión y copiarlos a la nueva instancia
        // 2 - Crear la nueva versión
        // 3 - Cargar los datos en las tablas de la nueva versión
    }

    private void cerrarBaseDatos (SQLiteDatabase database){
        database.close();
    }

    public void insertarDni(Dni dni){
        SQLiteDatabase database = null;
        try {
            database = this.getWritableDatabase();
            database.execSQL("INSERT INTO DNI (numero, letra) VALUES ("+ dni.getNumero()+" ," +
                    "'"+dni.getLetra()+"')");
        }catch (Exception e){
            Log.e("MIAPP", "Error inserción de datos"+e);
        }finally {
            this.cerrarBaseDatos(database);
        }
    }

    public Dni buscarDniLetra(String numero) {
        Dni dni = null;

        String numeroAux = "";
        String letra = "";
        final String CONSULTA = "SELECT * FROM DNI WHERE numero LIKE %"+ numero +"%";

        SQLiteDatabase basedatos = this.getReadableDatabase();
        Cursor cursor = basedatos.rawQuery(CONSULTA, null);


        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();

            numero = cursor.getString(1); //la posicion primera, numero
            letra = cursor.getString(2); //la posicion segunda, letra
            dni = new Dni(Integer.valueOf(numeroAux), letra.charAt(0));
            cursor.close();
        }
        this.cerrarBaseDatos(basedatos);
        return dni;
    }

    public ArrayList<Dni> buscarDnis() {
        ArrayList<Dni> lista_dnis = null;
        String letra = "";
        int numero = 0;
        int id = -1;
        Dni dniAux = null;

        final String CONSULTA = "SELECT * FROM DNI";

        SQLiteDatabase basedatos = this.getReadableDatabase();
        Cursor cursor = basedatos.rawQuery(CONSULTA, null);

        if (cursor != null && cursor.getCount() > 0){
            cursor.moveToFirst();
            lista_dnis = new ArrayList<Dni>(cursor.getCount());
            do{
                numero = cursor.getInt(1);
                letra = cursor.getString(2);
                dniAux = new Dni(numero,letra.charAt(0));
                lista_dnis.add(dniAux);
            }while (cursor.moveToNext());
            cursor.close();
        }

        this.cerrarBaseDatos(basedatos);
        return lista_dnis;
    }
}
