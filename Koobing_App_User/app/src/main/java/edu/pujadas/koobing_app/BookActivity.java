package edu.pujadas.koobing_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

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


        //recollint el intent en format json y transformant-lo a objecte
        String jsonLlibre = getIntent().getStringExtra("llibreJson");
        Gson gson = new Gson();
        Llibre llibre = gson.fromJson(jsonLlibre, Llibre.class);



        /// todo fix this they are empty

        //afegint tots els camps de llibre
       titolLlibre.setText(llibre.getTitol());
        autorName.setText(llibre.getAutor().getNomAutor());
        //editorial.setText(llibre.getEditor().getNomEditor());
        //genere.setText(llibre.getGenere().getNomGenere());
        //idioma.setText(llibre.getIdioma().getNomIdioma());
        //edicio.setText(llibre.getVersio());
       //dataPublicacio.setText(llibre.getDataPubli().toString());

    }
}