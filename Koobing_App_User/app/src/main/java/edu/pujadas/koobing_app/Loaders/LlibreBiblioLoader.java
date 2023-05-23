package edu.pujadas.koobing_app.Loaders;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import edu.pujadas.koobing_app.Models.Biblioteca;
import edu.pujadas.koobing_app.Models.Llibre;
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

        ArrayList<LlibreBiblioteca> listLLibresBiblio = new ArrayList<LlibreBiblioteca>();
        //String url = "http://192.168.0.33:3000/booksBiblio/";
        String url = "http://192.168.16.254:3000/booksBiblio/";

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
                                llibreBiblioteca.setId(jsonObject.getInt("id"));

                                //llibre objecte
                                Llibre book = new Llibre();
                                book.setISBN(jsonObject.getLong("ISBN"));
                                book.setTitol(jsonObject.getString("titol"));

                                //bilbioteca objecte
                                Biblioteca biblioteca = new Biblioteca();
                                biblioteca.setIdBiblioteca(jsonObject.getInt("id_biblioteca"));
                                biblioteca.setNomBiblioteca(jsonObject.getString("nom_biblio"));

                                llibreBiblioteca.setStock(jsonObject.getInt("stock"));
                                llibreBiblioteca.setBiblioteca(biblioteca); // afegint la biblioteca
                                llibreBiblioteca.setBook(book); //afegint el llibre


                                listLLibresBiblio.add(llibreBiblioteca);



                            }
                            callback.onSuccess(listLLibresBiblio);


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
