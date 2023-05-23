package edu.pujadas.koobing_app.Loaders;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

import edu.pujadas.koobing_app.Models.LlibreBiblioteca;
import edu.pujadas.koobing_app.Models.Usuari;
import edu.pujadas.koobing_app.Services.ApiCallback;
import edu.pujadas.koobing_app.Services.LlibreBiblioService;
import edu.pujadas.koobing_app.Services.LlibreService;
import edu.pujadas.koobing_app.Services.UserService;
import okhttp3.ResponseBody;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LlibreBiblioLoader {

    private LlibreBiblioService servei;


    public LlibreBiblioLoader() {

    }

    public void obtenirLlibresBiblio(final ApiCallback<List<LlibreBiblioteca>> callback)
    {
        String url = "http://192.168.0.33:3000/booksBiblio/";
        //String url = "http://192.168.16.254:3000/users/" + correo+"/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        servei = retrofit.create(LlibreBiblioService.class);


        Call<ResponseBody> call = servei.getAllBooksBiblioteca();

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
                            JSONArray jsonArray = new JSONArray(jsonResponse);


                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                LlibreBiblioteca llibreBiblioteca = new LlibreBiblioteca();

                                llibreBiblioteca.setId();



                            }
                            /*Usuari usuari = new Usuari();
                            usuari.setId(jsonObject.getInt("id_usuari"));
                            usuari.setDni(jsonObject.getString("dni"));
                            usuari.setNom(jsonObject.getString("nom"));
                            usuari.setCognom(jsonObject.getString("cognom"));

                            //datanaix
                            String fecha = jsonObject.getString("data_naix");
                            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                            formatoFecha.setTimeZone(TimeZone.getTimeZone("UTC"));
                            java.util.Date utilDate = formatoFecha.parse(fecha);
                            Date sqlDate = new java.sql.Date(utilDate.getTime());
                            usuari.setDataNaix(sqlDate);

                            usuari.setEmail(jsonObject.getString("email"));
                            usuari.setPassword(jsonObject.getString("password"));


                            callback.onSuccess(usuari);*/


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
