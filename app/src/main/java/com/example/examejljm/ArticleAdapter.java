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

        // Botón para abrir el PDF
        holder.btnOpenPdf.setOnClickListener(v -> {
            String pdfUrl = article.getPdfUrl();
            if (pdfUrl != null) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(pdfUrl));
                context.startActivity(intent);
            }
        });

        // Botón para descargar el PDF
        holder.btnDownloadPdf.setOnClickListener(v -> {
            String pdfUrl = article.getPdfUrl();
            if (pdfUrl != null) {
                downloadPdf(pdfUrl);
            }
        });

        // Botón para abrir en HTML con el DOI
        holder.btnOpenHtml.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(article.doi));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public static class ArticleViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle, txtAuthors;
        Button btnOpenPdf, btnDownloadPdf, btnOpenHtml;

        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(com.example.exameJLJM.R.id.txtTitle);
            txtAuthors = itemView.findViewById(R.id.txtAuthors);
            btnOpenPdf = itemView.findViewById(R.id.btnOpenPdf);
            btnDownloadPdf = itemView.findViewById(R.id.btnDownloadPdf);
            btnOpenHtml = itemView.findViewById(R.id.btnOpenHtml);
        }
    }

    // Método para descargar el PDF
    private void downloadPdf(String url) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setTitle("Descargando PDF");
        request.setDescription("Descargando artículo...");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "articulo.pdf");

        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        if (downloadManager != null) {
            downloadManager.enqueue(request);
        }
    }
}
