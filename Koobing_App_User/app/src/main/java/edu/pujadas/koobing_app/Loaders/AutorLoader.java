package edu.pujadas.koobing_app.Loaders;

import com.google.gson.JsonObject;

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
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AutorLoader {


    public void getAutorById(int id, final ApiCallback<Autor> callback) {
        String url = "http://localhost:3000/author/" + id;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AutorService service = retrofit.create(AutorService.class);
        Call<ResponseBody> call = service.getAutorById(id);

        call.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful())
                {
                    try{
                        String json = response.body().string();

                        JSONObject jsonObject = new JSONObject(json);
                        Autor autor = new Autor();
                        autor.setIdAutor(jsonObject.getInt("id_autor"));
                        autor.setNomAutor(jsonObject.getString("nom_autor"));
                        //convertir data naix
                        String fecha = jsonObject.getString("data_naix");
                        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                        formatoFecha.setTimeZone(TimeZone.getTimeZone("UTC"));
                        java.util.Date utilDate = formatoFecha.parse(fecha);
                        Date sqlDate = new java.sql.Date(utilDate.getTime());

                        autor.setDataNaixAutor(sqlDate);
                        callback.onSuccess(autor);

                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                else
                {
                    // error envio codi error
                    callback.onError(response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onFailure(t);
            }
        });



    }
}
