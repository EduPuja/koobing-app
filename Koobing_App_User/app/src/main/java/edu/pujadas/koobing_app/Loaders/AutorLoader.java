package edu.pujadas.koobing_app.Loaders;

import org.json.JSONObject;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import edu.pujadas.koobing_app.Models.Autor;
import edu.pujadas.koobing_app.Services.ApiCallback;
import edu.pujadas.koobing_app.Services.AutorService;
import edu.pujadas.koobing_app.Utilites.RetrofitConnection;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AutorLoader {


    public void getAutorByID(int id, final ApiCallback<Autor> callback)
    {
        String url = "http://192.168.0.33:3000/autor/" + id+"/";

        RetrofitConnection retrofitConnection = new RetrofitConnection(url);

        //creant el servei de post
        AutorService autorService = retrofitConnection.getRetrofit().create(AutorService.class);

        Call<ResponseBody> call = autorService.getAutorById(id);

        call.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful())
                {
                    String jsonResponse = null;
                    try{
                        jsonResponse = response.body().string();

                        if(jsonResponse != null )
                        {
                            JSONObject jsonObject = new JSONObject(jsonResponse);
                            Autor autor = new Autor();
                            autor.setIdAutor(jsonObject.getInt("id_autor"));
                            autor.setNomAutor(jsonObject.getString("nom_autor"));

                            // convertint la data de naiamnet
                            String fecha = jsonObject.getString("data_naix");
                            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                            formatoFecha.setTimeZone(TimeZone.getTimeZone("UTC"));
                            java.util.Date utilDate = formatoFecha.parse(fecha);
                            Date sqlDate = new java.sql.Date(utilDate.getTime());
                            autor.setDataNaixAutor(sqlDate);

                            //envio el callback suscces

                            callback.onSuccess(autor);
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                else
                {
                    //en cas d'error envio el codi
                    callback.onError(response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }
}
