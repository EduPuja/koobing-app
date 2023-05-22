package edu.pujadas.koobing_app.Loaders;

import java.util.List;

import edu.pujadas.koobing_app.Models.Usuari;
import edu.pujadas.koobing_app.Services.UserService;
import edu.pujadas.koobing_app.Services.ApiCallback;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserLoader {

    private UserService userService;

    //private String url = "http://192.168.0.33:3000/users/";

    //private String url = "http://192.168.16.254:3000/users/";
    private String url ="http://192.168.16.254:3000/users/";
    public UserLoader()
    {

    }

    public void obtenerUsuarios(final ApiCallback<List<Usuari>> callback) {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        userService = retrofit.create(UserService.class);


        Call<List<Usuari>> call = userService.getUsuaris();
        call.enqueue(new Callback<List<Usuari>>() {
            @Override
            public void onResponse(Call<List<Usuari>> call, Response<List<Usuari>> response) {
                if (response.isSuccessful()) {


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

    public Usuari obtenerUsuarioPorCorreo(String correo, final ApiCallback<Usuari> callback) {

        String url = "http://192.168.16.254:3000/users/" + correo;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        userService = retrofit.create(UserService.class);


        Call<Usuari> call = userService.getUserByEmail(correo);

        call.enqueue(new Callback<Usuari>() {

            @Override
            public void onResponse(Call<Usuari> call, Response<Usuari> response) {
                if(response.isSuccessful())
                {
                    Usuari user = response.body();

                    if(user != null)
                    {
                        // en cas de que sigui success envio el usuari
                        callback.onSuccess(user);
                    }


                }
                else
                {
                    //en cas d'error envio el codi d'error
                    callback.onError(response.code());
                }
            }

            @Override
            public void onFailure(Call<Usuari> call, Throwable t) {

                // en cas d'error envio l'error
                callback.onFailure(t);
            }
        });


        return null;

    }

}
