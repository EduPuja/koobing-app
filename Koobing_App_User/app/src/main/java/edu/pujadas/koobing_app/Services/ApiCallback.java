package edu.pujadas.koobing_app.Services;

public interface ApiCallback<T> {
    void onSuccess(T data);
    void onError(int statusCode);
    void onFailure(Throwable throwable);
}
