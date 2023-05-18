package edu.pujadas.koobing_app.Utilites;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

import java.util.ArrayList;

import edu.pujadas.koobing_app.Models.Usuari;

public class UserLoader {


    private Context context;
    private ArrayList<Usuari> listUsers;

    private String url = "http://192.168.19.0:3000/users";

    public UserLoader(Context context, ArrayList<Usuari> listUsers) {
        this.context = context;
        this.listUsers = listUsers;
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


    public ArrayList<Usuari> loadUsers()
    {
        try
        {
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray response) 
                {

                }



            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("Volley Error: " + error.getMessage());
                }
            });

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }


}
