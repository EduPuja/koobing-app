package edu.pujadas.koobing_app.Services;

import java.util.List;

import edu.pujadas.koobing_app.Models.Llibre;
import edu.pujadas.koobing_app.Models.LlibreBiblioteca;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface LlibreBiblioService {

    @GET("/booksBiblio")
    Call<ResponseBody> getAllBooksBiblioteca();
}
