package edu.pujadas.koobing_app.Loaders;

import org.json.JSONObject;

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


    /**
     * Metode que carregar el idioma per el id
     * @param id id per buscar
     * @param callback callback de tipus idioma
     */
    public void getIdiomaById(int id, final ApiCallback<Idioma> callback) {
        String url = "http://192.168.0.33:3000/idioma/" + id+"/";
        //String url = "http://192.168.16.254:3000/idioma/" + id+"/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IdiomaService service = retrofit.create(IdiomaService.class);
        Call<ResponseBody> call = service.getIdiomaById(id);
        call.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful())
                {
                    try
                    {
                        String json= response.body().string();
                        JSONObject jsonObject = new JSONObject(json);

                        Idioma idioma =new Idioma();
                        idioma.setIdIdioma(jsonObject.getInt("id_idioma"));
                        idioma.setNomIdioma(jsonObject.getString("nom_idioma"));

                        callback.onSuccess(idioma);
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
