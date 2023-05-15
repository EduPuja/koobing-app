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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

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


    public void carregarDades(View vista) {
        String url = "http://192.168.19.0:3000/users";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        // procesar la respuesta como una matriz JSON
                        homeLable.setText("Success!");
                        Toast.makeText(HomeActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                        System.out.println("Success");


                        List<Usuari> listUsers = new ArrayList<Usuari>();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                int id = jsonObject.getInt("id_usuari");
                                String dni = jsonObject.getString("dni");
                                //todo recollir avatar
                                String nom = jsonObject.getString("nom");
                                String cognom = jsonObject.getString("cognom");
                                String dataNaix = jsonObject.getString("data_naix");
                                String email = jsonObject.getString("email");
                                String password = jsonObject.getString("password");

                                Usuari u = new Usuari();
                                u.setDni(dni);
                                u.setNom(nom);
                                u.setCognom(cognom);
                                u.setEmail(email);
                                u.setPassword(password);

                                listUsers.add(u);

                            }
                            catch (JSONException e)
                            {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // manejar el error
                        //homeLable.setText("Error");
                        Toast.makeText(HomeActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                        System.out.println("Error " + error.getMessage());
                    }
                });
        // Crea una nueva cola de solicitudes de red.
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        // Agrega la solicitud a la cola de solicitudes.
        requestQueue.add(jsonArrayRequest);
    }

}