package com.example.examejljm;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exameJLJM.R;
import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {

    private Context context;
    private List<Article> articleList;

    public ArticleAdapter(Context context, List<Article> articleList) {
        this.context = context;
        this.articleList = articleList;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.articleitem, parent, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        Article article = articleList.get(position);
        holder.txtTitle.setText(article.title);
        holder.txtAuthors.setText(article.getAuthorsString());

        holder.btnOpenPdf.setOnClickListener(v -> {
            String pdfUrl = article.getPdfUrl();
            if (pdfUrl != null && !pdfUrl.isEmpty()) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(pdfUrl));
                context.startActivity(intent);
            }
        });

        holder.btnOpenHtml.setOnClickListener(v -> {
            if (article.doi != null && !article.doi.isEmpty()) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(article.doi));
                context.startActivity(intent);
            }
        });

        holder.btnDownloadPdf.setOnClickListener(v -> {
            String pdfUrl = article.getPdfUrl();
            if (pdfUrl != null && !pdfUrl.isEmpty()) {
                startPdfDownload(pdfUrl, article.title);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public static class ArticleViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle, txtAuthors;
        Button btnOpenPdf, btnOpenHtml, btnDownloadPdf;

        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtAuthors = itemView.findViewById(R.id.txtAuthors);
            btnOpenPdf = itemView.findViewById(R.id.btnOpenPdf);
            btnOpenHtml = itemView.findViewById(R.id.btnOpenHtml);
            btnDownloadPdf = itemView.findViewById(R.id.btnDownloadPdf);
        }
    }

    private void startPdfDownload(String url, String fileName) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setTitle("Descargando: " + fileName);
        request.setDescription("Descargando PDF...");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName.replaceAll(" ", "_") + ".pdf");

        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        if (downloadManager != null) {
            downloadManager.enqueue(request);
        }
    }
}
