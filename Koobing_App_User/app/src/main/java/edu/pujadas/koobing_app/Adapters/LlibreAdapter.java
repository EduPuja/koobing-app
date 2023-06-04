package edu.pujadas.koobing_app.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.List;

import edu.pujadas.koobing_app.BookActivity;
import edu.pujadas.koobing_app.Models.Llibre;
import edu.pujadas.koobing_app.R;

public class LlibreAdapter extends RecyclerView.Adapter<LlibreAdapter.LlibreViewHolder> {

    private static List<Llibre> listLlibres;

    public void setFiltredList(List<Llibre> filtredList)
    {
        this.listLlibres=filtredList;
        notifyDataSetChanged();
    }
    public LlibreAdapter(List<Llibre> listLlibres) {
        this.listLlibres = listLlibres;
    }

    @NonNull
    @Override
    public LlibreAdapter.LlibreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.llibre_items ,parent,false);

        return new LlibreViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull LlibreAdapter.LlibreViewHolder holder, int position) {
        String title = listLlibres.get(position).getTitol();
        holder.setData(title);
    }

    @Override
    public int getItemCount() {
        return listLlibres.size();
    }

    public static class  LlibreViewHolder extends RecyclerView.ViewHolder {
        ImageView portada;
        TextView titol;

        public LlibreViewHolder(@NonNull View itemView) {
            super(itemView);
            portada = itemView.findViewById(R.id.portada);
            titol = itemView.findViewById(R.id.bookTitle);

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION)
                    {
                        Llibre book = listLlibres.get(position);
                        Intent intent = new Intent(itemView.getContext(), BookActivity.class);
                        // convercio a json del objecte
                        Gson gson = new Gson();
                        String jsonBook = gson.toJson(book);


                        intent.putExtra("bookGson",jsonBook);
                        itemView.getContext().startActivity(intent);
                    }
                }
            });


        }

        // metode per afegir les dades del reycler view
        public void setData(String title) {
            this.titol.setText(title);
        }
    }
}
