package edu.pujadas.koobing_app.Services;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GenereService {

    @GET("/genere/{id}")
    Call<ResponseBody> getGenerelById(@Path("id") int id);
}
