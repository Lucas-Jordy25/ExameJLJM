package com.example.examejljm;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exameJLJM.R;

import java.util.List;

public class VolumeAdapter extends RecyclerView.Adapter<VolumeAdapter.VolumeViewHolder> {

    private List<Volumen> volumenList;
    private OnVolumeClickListener listener;

    public interface OnVolumeClickListener {
        void onVolumeClick(Volumen volumen);
    }

    public VolumeAdapter(List<Volumen> volumenList, OnVolumeClickListener listener) {
        this.volumenList = volumenList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VolumeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_volumen, parent, false);
        return new VolumeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VolumeViewHolder holder, int position) {
        Volumen volumen = volumenList.get(position);
        holder.tvVolumeTitle.setText(volumen.getTitle());
        holder.itemView.setOnClickListener(v -> listener.onVolumeClick(volumen));
    }

    @Override
    public int getItemCount() {
        return volumenList.size();
    }

    public static class VolumeViewHolder extends RecyclerView.ViewHolder {
        TextView tvVolumeTitle;

        public VolumeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvVolumeTitle = itemView.findViewById(R.id.tvVolumeTitle);
        }
    }
}
