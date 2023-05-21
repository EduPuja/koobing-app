package edu.pujadas.koobing_app.Loaders;

import edu.pujadas.koobing_app.Services.LlibreService;
import edu.pujadas.koobing_app.Services.UserService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LlibreBibliotecaLoader {
    private LlibreService  llibreService;

    private String url = "http://192.168.0.33:3000/books/";


    public LlibreBibliotecaLoader() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url) // Reemplaza con la URL base de tu API
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        llibreService = retrofit.create(LlibreService.class);
    }
}
