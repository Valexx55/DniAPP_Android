package com.example.dniapp.persistence;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.dniapp.actividades.MainActivity;
import com.example.dniapp.beans.Dni;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * Clase para alamacenar las prefrencias del usuario en XML
 *
 * @author VMG
 * @since 1.0
 * @see com.example.dniapp.actividades.MainActivity
 */
public class Preferencias {



    public static final String FICHERO_ULTIMO = "ultimo_dni";
    public static final String CLAVE_ULTIMO_DNI = "ultimo";

    public static final String CLAVE_ID_RADIO = "idradio";

    public static final String FICHERO_DNIS = "registros_dni";
    public static final String CLAVE_DNI = "num_registro";

    private static final String FICHERO_CONTADOR = "contador";
    private static final String CLAVE_CONTADOR = "id";


    /**
     * Este método graba en el fichero de preferencias el último dni
     * introducido por el usuario
     *
     * @param context el contexto de la aplicación
     * @param dni el valor a guardar del dni
     */
    public static void guardarDNI (Context context, String dni)
    {
        SharedPreferences sp = context.getSharedPreferences(FICHERO_ULTIMO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(CLAVE_ULTIMO_DNI, dni);
        editor.commit();

    }

    /**
     * Obtiene el valor del último dni alamcenado en las preferencias
     *
     * @param context el contexto de la aplicación
     * @return el ultimo dni almacenado y cadena vacía en caso de que no exista
     */
    public static String obtenerUltimoDNI (Context context)
    {
        String ultimo_dni = null;

            SharedPreferences sp = context.getSharedPreferences(FICHERO_ULTIMO, Context.MODE_PRIVATE);
            ultimo_dni = sp.getString(CLAVE_ULTIMO_DNI, "");
            

        return  ultimo_dni;
    }


    public static void guardarRadioActivo (Context context, int id_radio)
    {
        SharedPreferences sp = context.getSharedPreferences(FICHERO_ULTIMO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(CLAVE_ID_RADIO, id_radio);
        editor.commit();

    }

    public static int obtenerRadioActivo (Context context)
    {
        int idradio = 0;

            SharedPreferences sp = context.getSharedPreferences(FICHERO_ULTIMO, Context.MODE_PRIVATE);
            idradio = sp.getInt(CLAVE_ID_RADIO, 0 );

        return  idradio;
    }

    private static String obtenerIdSiguienteYActualiza (Context context)
    {
        String clave_siguiente = null;
        int num_aux = 0;

            SharedPreferences sp = context.getSharedPreferences(FICHERO_CONTADOR, Context.MODE_PRIVATE);
            num_aux = sp.getInt(CLAVE_CONTADOR, -1);
            num_aux++;
            clave_siguiente = String.valueOf(num_aux); //obtengo el id
            SharedPreferences.Editor editor = sp.edit();//guardo el nuevo id
            editor.putInt(CLAVE_CONTADOR, num_aux);
            editor.commit();

        return clave_siguiente;

    }

    public static void guardarDNIJson (Context context, String dni_json)
    {
        String nueva_clave = null;

            SharedPreferences sharedPreferences = context.getSharedPreferences(FICHERO_DNIS, Context.MODE_PRIVATE);
            nueva_clave = obtenerIdSiguienteYActualiza(context);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(nueva_clave, dni_json);
            editor.commit();
    }

    /**
     * Mostar por Log el contenudo del fichero de DNI's
     * @param context
     */
   public static List<Dni>  cargarFicheroDni (Context context)
   {
       List<Dni> lista_dnis = null;
       String clave_actual = null;
       String dni_actual = null;
       Dni objeto_dni = null;
       Gson gson = null;

           Log.d(MainActivity.TAG_APP, "Mostrando . . .");
           SharedPreferences sp = context.getSharedPreferences(FICHERO_DNIS, Context.MODE_PRIVATE);
           Map<String, String> mapa_dnis = (Map<String, String>)sp.getAll();
           //recorrer el mapa_dnis
           Set<String> claves = mapa_dnis.keySet();
           gson = new Gson();
           lista_dnis = new ArrayList<Dni>();

           for (String clave : claves) {
               dni_actual = mapa_dnis.get(clave);//obtengo el valor asociado a la clave
               Log.d(MainActivity.TAG_APP, dni_actual);
               objeto_dni = gson.fromJson(dni_actual, Dni.class);//y deserializo
               lista_dnis.add(objeto_dni);//añado a la lista el objeto Dni obtenido
           }

       /*Iterator<String> iterator = claves.iterator();

       while (iterator.hasNext())
       {
           clave_actual = iterator.next();
           dni_actual = mapa_dnis.get(clave_actual);
           Log.d(MainActivity.TAG_APP, dni_actual);
       }*/

    return lista_dnis;
   }
}
