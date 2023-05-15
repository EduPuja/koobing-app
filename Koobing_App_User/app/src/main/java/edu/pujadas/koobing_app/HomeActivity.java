package edu.pujadas.koobing_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONObject;




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


    public void carregarDades(View vista) {
        String url = "http://192.168.19.0:3000/users";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        homeLable.setText("Success!");
                        Toast.makeText(HomeActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                        System.out.println("Success");
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        homeLable.setText("Error");
                        Toast.makeText(HomeActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                        System.out.println("Error");
                    }
                });

        // Crea una nueva cola de solicitudes de red.
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        // Agrega la solicitud a la cola de solicitudes.
        requestQueue.add(jsonObjectRequest);
    }

}