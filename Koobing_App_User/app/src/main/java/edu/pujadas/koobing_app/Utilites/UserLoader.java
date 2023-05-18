package edu.pujadas.koobing_app.Utilites;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import edu.pujadas.koobing_app.Models.Usuari;

public class UserLoader {

    private static ArrayList<Usuari> listUsers = new ArrayList<Usuari>();



   /* public static ArrayList<Usuari> loadUsers(Context context) {

        String url = "http://192.168.19.0:3000/users";

        final ArrayList<Usuari> listUsers = new ArrayList<Usuari>();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        // procesar la respuesta como una matriz JSON
                        System.out.println("Success");

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

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // manejar el error
                        System.out.println("Error " + error.getMessage());
                    }
                });

        // Crea una nueva cola de solicitudes de red.
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        // Agrega la solicitud a la cola de solicitudes.
        requestQueue.add(jsonArrayRequest);

        return listUsers;
    }*/


    public static ArrayList<Usuari> getAllUsers(Context context)
    {
        String url = "http://192.168.19.0:3000/users";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        // procesar la respuesta como una matriz JSON
                        System.out.println("Success");

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


                                System.out.println("Data Naix: "+dataNaix);
                                Usuari u = new Usuari();
                                u.setId(id);
                                u.setDni(dni);
                                u.setNom(nom);
                                u.setCognom(cognom);
                                u.setEmail(email);
                                u.setPassword(password);

                                listUsers.add(u);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // manejar el error
                        System.out.println("Error " + error.getMessage());
                    }
                });

        // Crea una nueva cola de solicitudes de red.
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        // Agrega la solicitud a la cola de solicitudes.
        requestQueue.add(jsonArrayRequest);


        return listUsers;
    }
}
