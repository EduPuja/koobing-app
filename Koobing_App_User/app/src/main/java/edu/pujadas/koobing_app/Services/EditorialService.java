package edu.pujadas.koobing_app.Services;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface EditorialService {

    @GET("/editorial/{id}")
    Call<ResponseBody> getEditorialById(@Path("id") int id);
}
