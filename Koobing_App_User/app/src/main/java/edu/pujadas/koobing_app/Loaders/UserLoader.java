package edu.pujadas.koobing_app.Loaders;

import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.List;
import java.util.TimeZone;

import edu.pujadas.koobing_app.Models.Usuari;
import edu.pujadas.koobing_app.Services.UserService;
import edu.pujadas.koobing_app.Services.ApiCallback;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserLoader {

    private UserService userService;

    //private String url = "http://192.168.0.33:3000/users/";

    //private String url = "http://192.168.16.254:3000/users/";

    public UserLoader()
    {

    }

    public void obtenerUsuarios(final ApiCallback<List<Usuari>> callback) {


        //String url ="http://192.168.16.254:3000/users/";

        String url ="http://192.168.0.33:3000/users/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        userService = retrofit.create(UserService.class);


        Call<List<Usuari>> call = userService.getUsuaris();
        call.enqueue(new Callback<List<Usuari>>() {
            @Override
            public void onResponse(Call<List<Usuari>> call, Response<List<Usuari>> response) {
                if (response.isSuccessful()) {


                    List<Usuari> usuarios = response.body();
                    callback.onSuccess(usuarios);
                } else {
                    callback.onError(response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Usuari>> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public Usuari obtenerUsuarioPorCorreo(String correo, final ApiCallback<Usuari> callback) {


        String url = "http://192.168.0.33:3000/users/" + correo+"/";
        //String url = "http://192.168.16.254:3000/users/" + correo+"/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        userService = retrofit.create(UserService.class);


        Call<ResponseBody> call = userService.getUserByEmail(correo);

        call.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {
                if(response.isSuccessful())
                {
                    String jsonResponse = null;
                    try {
                        jsonResponse = response.body().string();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }

                    if(jsonResponse != null)
                    {

                        try {
                            JSONObject jsonObject = new JSONObject(jsonResponse);

                            Usuari usuari = new Usuari();
                            usuari.setId(jsonObject.getInt("id_usuari"));
                            usuari.setDni(jsonObject.getString("dni"));
                            usuari.setNom(jsonObject.getString("nom"));
                            usuari.setCognom(jsonObject.getString("cognom"));

                            //datanaix
                            String fecha = jsonObject.getString("data_naix");
                            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                            formatoFecha.setTimeZone(TimeZone.getTimeZone("UTC"));
                            java.util.Date utilDate = formatoFecha.parse(fecha);
                            Date sqlDate = new java.sql.Date(utilDate.getTime());
                            usuari.setDataNaix(sqlDate);


                        }


                        catch (Exception e) {
                          e.printStackTrace();
                        }

                        //Gson gson = new Gson();
                        //Usuari usuariConvertit = gson.fromJson(jsonResponse, Usuari.class);

                        //System.out.println("info user? :"+  usuariConvertit.getId());
                    }

                }
                else
                {
                    //en cas d'error envio el codi d'error
                    callback.onError(response.code());
                }


            }



            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onFailure(t);
            }
        });//end callback


        return null;

    }

}
