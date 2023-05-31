package edu.pujadas.koobing_app.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.google.gson.Gson;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import edu.pujadas.koobing_app.BookActivity;
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

    private List<Llibre> listLlibres;

    private LayoutInflater layoutInflater;





    public CarouselAdapter(List<Llibre> listLlibres, LayoutInflater layoutInflater) {
        this.listLlibres = listLlibres;
        this.layoutInflater = layoutInflater;
    }



    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = layoutInflater.inflate(R.layout.carousel_item, container, false);


        ImageView portadaLLibre = view.findViewById(R.id.fotoLlibre);
        TextView bookTitleTextView = view.findViewById(R.id.titol);
        Button readButton = view.findViewById(R.id.reservarBtn);




        Llibre book = listLlibres.get(position);


        if(book !=null)
        {

            bookTitleTextView.setText(book.getTitol());

            readButton.setOnClickListener(v -> {
                Context context = v.getContext();

                //shared preferneces per passar la info del llibre
                SharedPreferences sharedPreferences = context.getSharedPreferences("bookInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                //convertir el llibre amb gson per poder-lo passar atraves de intent
                Gson gson = new Gson();
                String jsonBook = gson.toJson(book);

                editor.putString("book",jsonBook);

                Intent intent = new Intent(context, BookActivity.class);

                //start activity
                context.startActivity(intent);
            }); // end lisenser
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
        return listLlibres.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
