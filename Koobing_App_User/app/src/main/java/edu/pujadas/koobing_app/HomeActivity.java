package edu.pujadas.koobing_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import edu.pujadas.koobing_app.Models.Usuari;


public class HomeActivity extends AppCompatActivity {


    TextView homeLable ;
    BottomNavigationView bottomNavBar ;
    HorizontalScrollView scrollView;

    


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        setTitle("Home");

        homeLable = findViewById(R.id.homeLabel);
        bottomNavBar = findViewById(R.id.bottom_navigation_view);
        scrollView = findViewById(R.id.horizontalScrollView);



    }




}