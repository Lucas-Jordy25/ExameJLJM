package com.example.examejljm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.exameJLJM.R;

import java.util.List;

public class RevistaAdapter extends RecyclerView.Adapter<RevistaAdapter.RevistaViewHolder> {

    private List<Revista> revistaList;
    private Context context;

    public RevistaAdapter(List<Revista> revistaList, Context context) {
        this.revistaList = revistaList;
        this.context = context;
    }

    @NonNull
    @Override
    public RevistaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_revista, parent, false);
        return new RevistaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RevistaViewHolder holder, int position) {
        Revista revista = revistaList.get(position);
        holder.textViewTitulo.setText(revista.getTitulo());
        Glide.with(context).load(revista.getPortada()).into(holder.imageViewPortada);
    }

    @Override
    public int getItemCount() {
        return revistaList.size();
    }

    public static class RevistaViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewPortada;
        TextView textViewTitulo;

        public RevistaViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewPortada = itemView.findViewById(com.example.exameJLJM.R.id.imgPortada);
            textViewTitulo = itemView.findViewById(R.id.tvTituloRevista);
        }
    }
}
