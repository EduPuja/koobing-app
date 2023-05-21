package edu.pujadas.koobing_app.Loaders;

import java.util.List;

import edu.pujadas.koobing_app.Models.LlibreBiblioteca;
import edu.pujadas.koobing_app.Models.Usuari;
import edu.pujadas.koobing_app.Services.ApiCallback;
import edu.pujadas.koobing_app.Services.LlibreService;
import edu.pujadas.koobing_app.Services.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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


    public void obtenerLibrosfinal  (final ApiCallback<List<LlibreBiblioteca>> callback)
    {
        Call <List<LlibreBiblioteca>> call = llibreService.getAllBooks();
        call.enqueue(new Callback<List<LlibreBiblioteca>>() {

            @Override
            public void onResponse(Call<List<LlibreBiblioteca>> call, Response<List<LlibreBiblioteca>> response) {
                if (response.isSuccessful()) {
                    List<LlibreBiblioteca> librosBiblioteca = response.body();
                    callback.onSuccess(librosBiblioteca);
                }
                else {
                    callback.onError(response.code());
                }
            }

            @Override
            public void onFailure(Call<List<LlibreBiblioteca>> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }
}
