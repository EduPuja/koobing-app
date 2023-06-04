package edu.pujadas.koobing_app.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.pujadas.koobing_app.Models.Reserva;
import edu.pujadas.koobing_app.R;

public class ReservaAdapter  extends RecyclerView.Adapter<ReservaAdapter.ResevaViewHolder>{

    List<Reserva> listReserva;

    public ReservaAdapter(List<Reserva> listReserva) {
        this.listReserva = listReserva;
    }

    @NonNull
    @Override
    public ReservaAdapter.ResevaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.llibre_items ,parent,false);

        return new ReservaAdapter.ResevaViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservaAdapter.ResevaViewHolder holder, int position) {
        String titleLlibre = listReserva.get(position).getLlibre().getTitol();
        holder.setData(titleLlibre);
    }

    @Override
    public int getItemCount() {
        return listReserva.size();
    }

    public static class ResevaViewHolder extends RecyclerView.ViewHolder {

        ImageView portada;
        TextView titol;
        public ResevaViewHolder(@NonNull View itemView) {
            super(itemView);
            portada = itemView.findViewById(R.id.portada);
            titol = itemView.findViewById(R.id.bookTitle);

        }

        public void setData(String title) {
            this.titol.setText(title);
        }
    }
}
