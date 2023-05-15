package edu.pujadas.koobing_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.HorizontalScrollView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import edu.pujadas.koobing_app.Database.ConnexioMYSQL;
import edu.pujadas.koobing_app.Database.GestioLlibre;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavBar ;
    HorizontalScrollView scrollView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        setTitle("Home");

        ConnexioMYSQL con = new ConnexioMYSQL();
        con.conectar();




        bottomNavBar = findViewById(R.id.bottom_navigation_view);
        scrollView = findViewById(R.id.horizontalScrollView);

    }
}