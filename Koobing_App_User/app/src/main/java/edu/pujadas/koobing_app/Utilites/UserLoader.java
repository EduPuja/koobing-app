package edu.pujadas.koobing_app.Utilites;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;




import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

import edu.pujadas.koobing_app.Models.Usuari;

public class UserLoader {


    private Context context;
    private ArrayList<Usuari> listUsers;


    // ip insti 192.168.19.0
    // ip home : 192.168.0.33
    private String url = "http://192.168.0.33:3000/users";

    public UserLoader(Context context) {
        this.context = context;

    }


    public UserLoader() {
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<Usuari> getListUsers() {
        return listUsers;
    }

    public void setListUsers(ArrayList<Usuari> listUsers) {
        this.listUsers = listUsers;
    }


    public void loadUsers()
    {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
            new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response)
                {
                    System.out.println("Successfully loaded response");
                    try
                    {
                        listUsers = new ArrayList<Usuari>();
                        for (int i = 0; i < response.length(); i++)
                        {
                            JSONObject jsonObject = response.getJSONObject(i);
                            int idUsuari = jsonObject.getInt("id_usuari");
                            String nom =jsonObject.getString("nom");
                            Usuari usuari = new Usuari();
                            usuari.setId(idUsuari);
                            usuari.setNom(nom);

                            listUsers.add(usuari);

                            saveArrayList(listUsers);
                        }
                    }
                    catch (Exception e)
                    {
                        System.out.println("on response error: " + e.getMessage());
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("Volley Error: " + error.getMessage());
                }
            }); // fi jsonArrayRequest

        // Crea una nueva cola de solicitudes de red.
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        // Agrega la solicitud a la cola de solicitudes.
        requestQueue.add(jsonArrayRequest);
        requestQueue.stop();

    }

    private void saveArrayList(ArrayList<Usuari> listUsers) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String json = gson.toJson(listUsers);
        editor.putString("userList", json);
        editor.apply();
    }

    private ArrayList<Usuari> loadArrayList() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String json = sharedPreferences.getString("userList", null);

        Type type = new TypeToken<ArrayList<Usuari>>() {}.getType();
        Gson gson = new Gson();
        return gson.fromJson(json, type);
    }


}
