package edu.pujadas.koobing_app.Loaders;

import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.List;
import java.util.TimeZone;

import edu.pujadas.koobing_app.Models.Usuari;
import edu.pujadas.koobing_app.Services.UserService;
import edu.pujadas.koobing_app.Services.ApiCallback;
import edu.pujadas.koobing_app.Utilites.RetrofitConnection;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserLoader {

    private UserService userService;
    private RetrofitConnection retrofit;

    private String url = "http://192.168.0.33:3000/users/";

    //ip institut
    //private String url = "http://192.168.16.254:3000/users/";

    public UserLoader()
    {

    }


    public void obtenerUsuarioPorCorreo(String correo, final ApiCallback<Usuari> callback) {

        retrofit = new RetrofitConnection(url);

        userService = retrofit.getRetrofit().create(UserService.class);


        Call<ResponseBody> call = userService.getUserByEmail(correo);

        call.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {
                if(response.isSuccessful())
                {
                    String jsonResponse = null;
                    try {
                        jsonResponse = response.body().string();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }

                    if(jsonResponse != null)
                    {

                        try {
                            JSONObject jsonObject = new JSONObject(jsonResponse);

                            Usuari usuari = new Usuari();
                            usuari.setId(jsonObject.getInt("id_usuari"));
                            usuari.setDni(jsonObject.getString("dni"));
                            usuari.setNom(jsonObject.getString("nom"));
                            usuari.setCognom(jsonObject.getString("cognom"));



                            usuari.setEmail(jsonObject.getString("email"));
                            usuari.setPassword(jsonObject.getString("password"));


                            callback.onSuccess(usuari);


                        }


                        catch (Exception e) {
                          e.printStackTrace();
                        }
                    }

                }
                else
                {
                    //en cas d'error envio el codi d'error
                    callback.onError(response.code());
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onFailure(t);
            }
        });//end callback


    }

}
