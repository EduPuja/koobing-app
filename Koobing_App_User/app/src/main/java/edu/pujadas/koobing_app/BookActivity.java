package edu.pujadas.koobing_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.gson.reflect.TypeToken;

import com.google.gson.Gson;

import java.lang.reflect.Type;

import edu.pujadas.koobing_app.Models.Llibre;

public class BookActivity extends AppCompatActivity {

    TextView titolLlibre,autorName,editorial,genere,idioma,edicio,dataPublicacio;
    ImageView logoLlibre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_activity);


        //find all elements in xml file
        titolLlibre = findViewById(R.id.nomLlibre);
        autorName = findViewById(R.id.autorName);
        editorial = findViewById(R.id.editorialName);
        genere = findViewById(R.id.genereName);
        idioma = findViewById(R.id.idioma);
        edicio= findViewById(R.id.edicio);
        dataPublicacio = findViewById(R.id.dataPubli);

        /*SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);

        // Obtener la cadena JSON del objeto Libro
        String jsonLibro = sharedPreferences.getString("libro", null);

        // Convertir la cadena JSON a objeto Libro utilizando Gson
        Gson gson = new Gson();
        Type tipoLibro = new TypeToken<Llibre>() {}.getType();
        Llibre llibre = gson.fromJson(jsonLibro, tipoLibro);*/





        //afegint tots els camps de llibre

        //titolLlibre.setText(llibre.getTitol());
        //autorName.setText(llibre.getAutor().getNomAutor());
        //editorial.setText(llibre.getEditor().getNomEditor());
        //genere.setText(llibre.getGenere().getNomGenere());
        //idioma.setText(llibre.getIdioma().getNomIdioma());
        //edicio.setText(llibre.getVersio());
       //dataPublicacio.setText(llibre.getDataPubli().toString());

    }
}