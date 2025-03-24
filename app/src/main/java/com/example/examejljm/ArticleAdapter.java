package com.example.examejljm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exameJLJM.R;

import java.util.ArrayList;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {

    private Context context;
    private ArrayList<Articulo> articuloList;
    private OnArticleClickListener listener;

    public interface OnArticleClickListener {
        void onArticleClick(String doi);
    }

    public ArticleAdapter(Context context, ArrayList<Articulo> articuloList, OnArticleClickListener listener) {
        this.context = context;
        this.articuloList = articuloList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_article, parent, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        Articulo articulo = articuloList.get(position);
        holder.tvTituloArticulo.setText(articulo.getTitle());

        holder.itemView.setOnClickListener(v -> listener.onArticleClick(articulo.getDoi()));
    }

    @Override
    public int getItemCount() {
        return articuloList.size();
    }

    public static class ArticleViewHolder extends RecyclerView.ViewHolder {

        TextView tvTituloArticulo;

        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTituloArticulo = itemView.findViewById(com.example.exameJLJM.R.id.tvTituloArticulo);
        }
    }
}
