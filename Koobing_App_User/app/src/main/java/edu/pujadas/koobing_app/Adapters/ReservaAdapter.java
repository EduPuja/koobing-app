package edu.pujadas.koobing_app.Adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ReservaAdapter  extends RecyclerView.Adapter<ReservaAdapter.ResevaViewHolder>{
    @NonNull
    @Override
    public ReservaAdapter.ResevaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ReservaAdapter.ResevaViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ResevaViewHolder extends RecyclerView.ViewHolder {
        public ResevaViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
