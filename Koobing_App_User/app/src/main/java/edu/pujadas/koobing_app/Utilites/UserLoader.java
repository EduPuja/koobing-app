import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

import edu.pujadas.koobing_app.Models.Usuari;

public class UserLoader {
    private Context context;
    private RequestQueue requestQueue;
    private String url;

    public UserLoader(Context context, String url) {
        this.context = context;
        this.url = url;
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public void loadUsers(final UserLoadListener listener) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        ArrayList<Usuari> users = new ArrayList<>();

                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject userJson = response.getJSONObject(i);
                                int userId = userJson.getInt("id_usuari");
                                String dni = userJson.getString("dni");
                                String nom = userJson.getString("nom");
                                // Obtén otros campos del usuario según tu estructura de datos

                                Usuari user = new Usuari();
                                user.setId(userId);
                                user.setDni(dni);
                                user.setNom(nom);

                                //afegir al arraylist
                                users.add(user);
                            }

                            listener.onUsersLoaded(users);
                        } catch (JSONException e) {
                            listener.onLoadError("Error al procesar la respuesta del servidor");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onLoadError("Error de red: " + error.getMessage());
                    }
                });

        requestQueue.add(jsonArrayRequest);
    }

    public interface UserLoadListener {
        void onUsersLoaded(ArrayList<Usuari> users);
        void onLoadError(String error);
    }
}
