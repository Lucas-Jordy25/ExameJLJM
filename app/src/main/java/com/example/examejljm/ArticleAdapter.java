package com.example.examejljm;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exameJLJM.R;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {

    private List<Articulo> articuloList;
    private Context context;

    public ArticleAdapter(List<Articulo> articuloList, Context context) {
        this.articuloList = articuloList;
        this.context = context;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(com.example.exameJLJM.R.layout.item_article, parent, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        Articulo articulo = articuloList.get(position);
        holder.textViewTitulo.setText(articulo.getTitle());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(articulo.getDoi()));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return articuloList.size();
    }

    public static class ArticleViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitulo;

        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitulo = itemView.findViewById(R.id.tvTituloArticulo);
        }
    }
}
