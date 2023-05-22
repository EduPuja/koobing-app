package edu.pujadas.koobing_app.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

import edu.pujadas.koobing_app.Models.Llibre;
import edu.pujadas.koobing_app.R;

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
