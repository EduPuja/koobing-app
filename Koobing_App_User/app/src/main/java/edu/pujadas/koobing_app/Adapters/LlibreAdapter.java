package edu.pujadas.koobing_app.Adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.pujadas.koobing_app.Models.Llibre;

public class LlibreAdapter extends RecyclerView.Adapter<LlibreAdapter.LlibreViewHolder> {

    private List<Llibre> listLlibres;

    public LlibreAdapter(List<Llibre> listLlibres) {
        this.listLlibres = listLlibres;
    }

    @NonNull
    @Override
    public LlibreAdapter.LlibreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull LlibreAdapter.LlibreViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class LlibreViewHolder extends RecyclerView.ViewHolder {
        public LlibreViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
