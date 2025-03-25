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

public class AdapterIssue extends RecyclerView.Adapter<AdapterIssue.ViewHolder> {

    private Context context;
    private List<Issue> issueList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Issue issue);
    }

    public AdapterIssue(Context context, List<Issue> issueList, OnItemClickListener listener) {
        this.context = context;
        this.issueList = issueList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.issueitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Issue issue = issueList.get(position);
        holder.txtVolume.setText("Volumen: " + issue.volume + "  Num: " +issue.number);
        holder.txtYear.setText("Año: " + issue.year);
        holder.txttitle.setText(issue.title);
        holder.txtdate.setText(issue.date_published);

        Glide.with(context)
                .load(issue.cover)
                .placeholder(com.example.exameJLJM.R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_background)
                .into(holder.imgPortada);

        holder.itemView.setOnClickListener(v -> listener.onItemClick(issue));
    }

    @Override
    public int getItemCount() {
        return issueList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtVolume, txtYear, txttitle,txtdate;
        ImageView imgPortada;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPortada = itemView.findViewById(R.id.imgPortada);
            txtVolume = itemView.findViewById(R.id.txtVolume);
            txtYear = itemView.findViewById(R.id.txtYear);
            txttitle = itemView.findViewById(R.id.txttitle);
            txtdate = itemView.findViewById(R.id.txtfecha);

        }
    }
}

