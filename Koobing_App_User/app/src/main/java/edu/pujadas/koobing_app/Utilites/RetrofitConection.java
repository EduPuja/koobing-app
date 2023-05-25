package edu.pujadas.koobing_app.Utilites;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConection {

    private Retrofit retrofit;

    public RetrofitConection(String url) {
        this.retrofit = new Retrofit.Builder().
                baseUrl(url).
                addConverterFactory(GsonConverterFactory.create()).build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
