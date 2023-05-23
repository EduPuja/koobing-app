package edu.pujadas.koobing_app.Loaders;

import java.util.List;

import edu.pujadas.koobing_app.Models.Biblioteca;
import edu.pujadas.koobing_app.Services.BibliotecaService;
import edu.pujadas.koobing_app.Services.ApiCallback;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BibilotecaLoader {

    private BibliotecaService serviBiblioteca;

   // private String url = "http://192.168.0.33:3000/biblioteques/";
  private String url = "http://192.168.16.254:3000/biblioteques/";

    public BibilotecaLoader() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url) // Reemplaza con la URL base de tu API
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        //creacio del servei
        serviBiblioteca = retrofit.create(BibliotecaService.class);

    }

    /**
     * Metoodo para obtener una respuesta del servidor que te devuelva el listado de bilibtoecas
     * para poder poner en el mapa
     * @param callback ApiCallBack interficie
     */
    public void obtenerBiblioteques(final ApiCallback<List<Biblioteca>> callback)
    {
        Call<List<Biblioteca>> call = serviBiblioteca.getBiblioteques();
        call.enqueue(new Callback<List<Biblioteca>>() {
            @Override
            public void onResponse(Call<List<Biblioteca>> call, Response<List<Biblioteca>> response) {
                if(response.isSuccessful())
                {
                    List<Biblioteca> bibliotecas = response.body();
                    callback.onSuccess(bibliotecas);
                }
                else
                {
                    // en caso de que no sea succesfful la respuesta digo cual es el error
                    callback.onError(response.code());
                }
            }


            // en el caso de que falle envio el throweable que es com una exception
            @Override
            public void onFailure(Call<List<Biblioteca>> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }
}
