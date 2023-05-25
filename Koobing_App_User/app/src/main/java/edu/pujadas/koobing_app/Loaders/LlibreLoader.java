package edu.pujadas.koobing_app.Loaders;

import java.util.List;

import edu.pujadas.koobing_app.Models.Llibre;
import edu.pujadas.koobing_app.Services.ApiCallback;
import edu.pujadas.koobing_app.Services.LlibreService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LlibreLoader {
    private LlibreService  llibreService;

    //private String url = "http://192.168.0.33:3000/books/";
    private String url ="http://192.168.16.254:3000/books/";

    public LlibreLoader() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url) // Reemplaza con la URL base de tu API
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        llibreService = retrofit.create(LlibreService.class);
    }


    /**
     * Metode per obtenir tots els llibres de la API
     * @param callback callback de tipus llibreBiblioteca on hi h totala inforamcio del llibre i la biblioteca
     */
    public void obtenerLibrosfinal  (final ApiCallback<List<Llibre>> callback)
    {
        Call <List<Llibre>> call = llibreService.getAllBooks();
        call.enqueue(new Callback<List<Llibre>>() {

            @Override
            public void onResponse(Call<List<Llibre>> call, Response<List<Llibre>> response) {
                if (response.isSuccessful()) {
                    List<Llibre> librosBiblioteca = response.body();

                    callback.onSuccess(librosBiblioteca);
                }
                else {
                    callback.onError(response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Llibre>> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }
}
