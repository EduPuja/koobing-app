package edu.pujadas.koobing_app.Utilites;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConnection {

    private Retrofit retrofit;

    public RetrofitConnection(String url) {
        this.retrofit = new Retrofit.Builder().
                baseUrl(url).
                addConverterFactory(GsonConverterFactory.create()).build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
