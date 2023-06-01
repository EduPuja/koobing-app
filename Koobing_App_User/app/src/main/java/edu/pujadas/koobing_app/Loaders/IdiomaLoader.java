package edu.pujadas.koobing_app.Loaders;

import edu.pujadas.koobing_app.Models.Idioma;
import edu.pujadas.koobing_app.Services.ApiCallback;
import edu.pujadas.koobing_app.Services.IdiomaService;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class IdiomaLoader {

    public void getIdiomaById(int id, final ApiCallback<Idioma> callback) {
        String url = "http://localhost:3000/idioma/" + id;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IdiomaService service = retrofit.create(IdiomaService.class);
        Call<ResponseBody> call = service.getIdiomaById(id);
        call.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
