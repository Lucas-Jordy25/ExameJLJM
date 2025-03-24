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

public class VolumeAdapter extends RecyclerView.Adapter<VolumeAdapter.VolumeViewHolder> {

    private Context context;
    private ArrayList<Volumen> volumenes;
    private OnVolumeClickListener listener;

    public interface OnVolumeClickListener {
        void onVolumeClick(String issueId);
    }

    public VolumeAdapter(Context context, ArrayList<Volumen> volumenes, OnVolumeClickListener listener) {
        this.context = context;
        this.volumenes = volumenes;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VolumeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(com.example.exameJLJM.R.layout.item_volume, parent, false);
        return new VolumeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VolumeViewHolder holder, int position) {
        Volumen volumen = volumenes.get(position);
        holder.tvVolumeTitle.setText(volumen.getTitle());

        holder.itemView.setOnClickListener(v -> listener.onVolumeClick(volumen.getIssue_id()));
    }

    @Override
    public int getItemCount() {
        return volumenes.size();
    }

    public static class VolumeViewHolder extends RecyclerView.ViewHolder {

        TextView tvVolumeTitle;

        public VolumeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvVolumeTitle = itemView.findViewById(R.id.tvVolumeTitle);
        }
    }
}
