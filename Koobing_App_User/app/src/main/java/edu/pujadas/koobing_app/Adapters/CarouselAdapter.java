package edu.pujadas.koobing_app.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import edu.pujadas.koobing_app.Models.Llibre;
import edu.pujadas.koobing_app.Models.Reserva;
import edu.pujadas.koobing_app.Models.Treballador;
import edu.pujadas.koobing_app.Models.Usuari;
import edu.pujadas.koobing_app.R;
import edu.pujadas.koobing_app.Services.ReservaService;
import edu.pujadas.koobing_app.Utilites.UsuarioSingleton;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CarouselAdapter extends PagerAdapter {

    private List<Llibre> books;

    private LayoutInflater layoutInflater;





    public CarouselAdapter(List<Llibre> books, LayoutInflater layoutInflater) {
        this.books = books;
        this.layoutInflater = layoutInflater;
    }



    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = layoutInflater.inflate(R.layout.carousel_item, container, false);

        TextView bookTitleTextView = view.findViewById(R.id.bookTitleTextView);
        Button readButton = view.findViewById(R.id.reservarBtn);



        Llibre book = books.get(position);

        if(book !=null)
        {

            bookTitleTextView.setText(book.getTitol());
            readButton.setOnClickListener(v -> {

                Reserva reserva = new Reserva();

                reserva.setISBN(book.getISBN());
                Usuari currentUser = UsuarioSingleton.getInstance().getUsuario();
                reserva.setIdUsuari(currentUser.getId());
                //reserva.setBiblio();
                Treballador administrador = new Treballador();
                administrador.setId(1);
                administrador.setNom("Admin");
                administrador.setEmail("administrador@mail.com");
                administrador.setAdmin(true);

                reserva.setIdTreballador(administrador.getId());
                Date dataInici = Date.valueOf(String.valueOf(LocalDate.now()));
                Date dataFi = Date.valueOf(String.valueOf(LocalDate.now().plusMonths(1)));
                reserva.setDataInici(dataInici);


                //ip institut
                //String url = "http://192.168.16.254:3000/reservarLlibre/";

                //ip home
                String url = "http://192.168.0.33:3000/reservarLlibre/";
                OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(url)
                        .client(httpClient.build())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();


                ReservaService reservaService = retrofit.create(ReservaService.class);
                Call<Void> call = reservaService.hacerReserva(reserva);
                call.enqueue(new Callback<Void>() {

                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful())
                        {
                            System.out.println("Reserva creada ");
                            Toast.makeText(container.getContext(), "El llibre s'ha reservat correctamet", Toast.LENGTH_SHORT).show();

                        }
                        else
                        {
                            Toast.makeText(container.getContext(), "Error al realizar la reserva", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        System.out.println("onFailure :" + t.getMessage());
                        Toast.makeText(container.getContext(), "Error en la solicitud ", Toast.LENGTH_SHORT).show();
                    }
                }); //end call
            });
        }
        else
        {
            System.out.println("Llibre Buit");
        }


        container.addView(view);
        return view;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }


    @Override
    public int getCount() {
        return books.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
