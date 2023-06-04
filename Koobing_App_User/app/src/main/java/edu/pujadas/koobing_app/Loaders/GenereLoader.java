package edu.pujadas.koobing_app.Loaders;

import org.json.JSONObject;

import edu.pujadas.koobing_app.Models.Genere;
import edu.pujadas.koobing_app.Services.ApiCallback;
import edu.pujadas.koobing_app.Services.GenereService;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GenereLoader {

    public void getGenereById(int id, final ApiCallback<Genere> callback) {
        String url = "http://192.168.0.33:3000/genere/" + id +"/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GenereService service = retrofit.create(GenereService.class);
        Call<ResponseBody> call = service.getGenereById(id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful())
                {
                    try
                    {
                        String json = response.body().string();
                        JSONObject jsonObject = new JSONObject(json);
                        Genere genere =new Genere();
                        genere.setIdGenere(jsonObject.getInt("id_genere"));
                        genere.setNomGenere(jsonObject.getString("descrip"));

                        callback.onSuccess(genere);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                else {
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
