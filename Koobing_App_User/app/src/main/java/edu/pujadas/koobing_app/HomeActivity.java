package edu.pujadas.koobing_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.sql.Statement;

import edu.pujadas.koobing_app.Database.ConnexioMYSQL;
import edu.pujadas.koobing_app.Database.GestioLlibre;

public class HomeActivity extends AppCompatActivity {


    TextView homeLable ;
    BottomNavigationView bottomNavBar ;
    HorizontalScrollView scrollView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        setTitle("Home");

        homeLable = findViewById(R.id.homeLabel);

        ConnexioMYSQL con = new ConnexioMYSQL();







        bottomNavBar = findViewById(R.id.bottom_navigation_view);
        scrollView = findViewById(R.id.horizontalScrollView);

    }
}