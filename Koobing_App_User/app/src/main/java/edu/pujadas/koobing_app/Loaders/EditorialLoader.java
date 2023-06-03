package edu.pujadas.koobing_app.Loaders;

import org.json.JSONObject;

import edu.pujadas.koobing_app.Models.Editorial;
import edu.pujadas.koobing_app.Services.ApiCallback;
import edu.pujadas.koobing_app.Services.EditorialService;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditorialLoader {

    public void getEditorialById(int id, final ApiCallback<Editorial> callback) {
        String url = "http://192.168.0.33:3000/editor/" + id+"/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        EditorialService service = retrofit.create(EditorialService.class);
        Call<ResponseBody> call = service.getEditorialById(id);
        call.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful())
                {

                    try
                    {
                        String jsonResponse = response.body().string();

                        JSONObject json = new JSONObject(jsonResponse);
                        Editorial editorial = new Editorial();
                        editorial.setIdEditorial(json.getInt("id_editorial"));
                        editorial.setNomEditor(json.getString("nom_editorial"));

                        callback.onSuccess(editorial);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                else
                {
                    callback.onError(response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }

   /* public void getEditorialById(int id, final ApiCallback<Editorial> callback) {
        String url = "http://192.168.0.33:3000/editor/" + id+"/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        EditorialService service = retrofit.create(EditorialService.class);
        Call<Editorial> call = service.getEditorialById(id);
        call.enqueue(new Callback<Editorial>() {

            @Override
            public void onResponse(Call<Editorial> call, Response<Editorial> response) {
                if(response.isSuccessful())
                {

                    try
                    {
                        Editorial editorial = response.body();


                        callback.onSuccess(editorial);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                else
                {
                    callback.onError(response.code());
                }
            }

            @Override
            public void onFailure(Call<Editorial> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }*/

}
