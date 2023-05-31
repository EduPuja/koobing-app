package edu.pujadas.koobing_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;

import edu.pujadas.koobing_app.Models.Llibre;

public class BookActivity extends AppCompatActivity {

    TextView titolLlibre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_activity);

        titolLlibre = findViewById(R.id.nomLlibre);

        //recollint el intent en format json y transformant-lo a objecte
        String jsonLlibre = getIntent().getStringExtra("llibreJson");
        Gson gson = new Gson();
        Llibre llibre = gson.fromJson(jsonLlibre, Llibre.class);

        titolLlibre.setText(llibre.getTitol());
    }
}