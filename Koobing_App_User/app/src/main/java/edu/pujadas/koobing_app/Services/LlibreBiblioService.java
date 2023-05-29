package edu.pujadas.koobing_app.Services;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface LlibreBiblioService {

    @GET("/booksBiblio")
    Call<ResponseBody> getAllBooksBiblioteca();
}
