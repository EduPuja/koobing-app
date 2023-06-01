package edu.pujadas.koobing_app.Loaders;

import edu.pujadas.koobing_app.Models.Autor;
import edu.pujadas.koobing_app.Services.ApiCallback;
import edu.pujadas.koobing_app.Services.AutorService;
import edu.pujadas.koobing_app.Utilites.RetrofitConnection;

public class AutorLoader {


    public void getAutorByID(int id, final ApiCallback<Autor> callback)
    {
        String url = "http://192.168.0.33:3000/autor/" + id+"/";

        RetrofitConnection retrofitConnection = new RetrofitConnection(url);

        //creant el servei de post
        AutorService autorService = retrofitConnection.getRetrofit().create(AutorService.class);



    }
}
