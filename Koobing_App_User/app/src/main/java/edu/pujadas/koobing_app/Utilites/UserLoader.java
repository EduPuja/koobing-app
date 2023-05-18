package edu.pujadas.koobing_app.Utilites;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;

import java.lang.reflect.Type;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

import edu.pujadas.koobing_app.Models.Usuari;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserLoader {

    private ApiService apiService;

    private String url = "http://192.168.0.33:3000/users/";
    public UserLoader()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url) // Reemplaza con la URL base de tu API
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        apiService = retrofit.create(ApiService.class);
    }

    public void obtenerUsuarios(final ApiCallback<List<Usuari>> callback) {
        Call<List<Usuari>> call = apiService.getUsuaris();
        call.enqueue(new Callback<List<Usuari>>() {
            @Override
            public void onResponse(Call<List<Usuari>> call, Response<List<Usuari>> response) {
                if (response.isSuccessful()) {

                    // Crear el GsonBuilder
                    GsonBuilder gsonBuilder = new GsonBuilder();

                    // Registrar un InstanceCreator personalizado para Blob
                    gsonBuilder.registerTypeAdapter(Blob.class, new InstanceCreator<Blob>() {
                        @Override
                        public Blob createInstance(Type type) {
                            try {
                                return new SerialBlob(new byte[0]);
                            } catch (SQLException e) {
                                e.printStackTrace();
                                return null;
                            }
                        }
                    });

                    // Crear el Gson con el GsonBuilder configurado
                    Gson gson = gsonBuilder.create();





                    List<Usuari> usuarios = response.body();
                    callback.onSuccess(usuarios);
                } else {
                    callback.onError(response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Usuari>> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }
}
