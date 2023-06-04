package edu.pujadas.koobing_app.Loaders;

import org.json.JSONObject;

import edu.pujadas.koobing_app.Models.Reserva;
import edu.pujadas.koobing_app.Services.ApiCallback;
import edu.pujadas.koobing_app.Services.ReservaService;
import edu.pujadas.koobing_app.Services.UserService;
import edu.pujadas.koobing_app.Utilites.RetrofitConnection;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservaLoader {

    ReservaService reservaService;
    public void obtenriLlibresReservats(int idUsuari, ApiCallback<Reserva> callback)
    {
        //String url = "http://192.168.0.33:3000/llibresReservats/"+idUsuari+"/";
        String url = "http://192.168.16.254:3000/llibresReservats/"+idUsuari+"/";
        RetrofitConnection connectionRetro = new RetrofitConnection(url);
        reservaService = connectionRetro.getRetrofit().create(ReservaService.class);

        Call<ResponseBody> call = reservaService.obtenirLlibresReservats(idUsuari);
        call.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful())
                {
                    try
                    {
                        String json = response.body().string();
                        JSONObject jsonObject = new JSONObject(json);

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

}
