package edu.pujadas.koobing_app_user;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import edu.pujadas.koobing_app_user.Database.ConnexioMYSQL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Koobing APP");

    }
}