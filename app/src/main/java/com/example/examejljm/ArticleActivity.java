package com.example.examejljm;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exameJLJM.R;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ArticleActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArticleAdapter articleAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.exameJLJM.R.layout.activityarticle);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        int issueId = getIntent().getIntExtra("issue_id", -1);
        if (issueId != -1) {
            fetchArticles(issueId);
        } else {
            Toast.makeText(this, "Error al obtener la edición", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchArticles(int issueId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://revistas.uteq.edu.ec/ws/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ServiceA apiService = retrofit.create(ServiceA.class);
        apiService.getArticles(issueId).enqueue(new Callback<List<Article>>() {
            @Override
            public void onResponse(Call<List<Article>> call, Response<List<Article>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    articleAdapter = new ArticleAdapter(ArticleActivity.this, response.body());
                    recyclerView.setAdapter(articleAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Article>> call, Throwable t) {
                Toast.makeText(ArticleActivity.this, "Error al cargar artículos", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
