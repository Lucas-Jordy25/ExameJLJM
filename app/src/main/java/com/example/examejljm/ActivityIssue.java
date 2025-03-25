package com.example.examejljm;
import android.content.Intent;
import android.os.Bundle;
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

public class ActivityIssue extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdapterIssue issueAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.exameJLJM.R.layout.activityissue);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        int journalId = getIntent().getIntExtra("journal_id", 1);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://revistas.uteq.edu.ec/ws/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ServiceA apiService = retrofit.create(ServiceA.class);
        apiService.getIssues(journalId).enqueue(new Callback<List<Issue>>() {
            @Override
            public void onResponse(Call<List<Issue>> call, Response<List<Issue>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    issueAdapter = new AdapterIssue(ActivityIssue.this, response.body(), issue -> {
                        Intent intent = new Intent(ActivityIssue.this, ArticleActivity.class);
                        intent.putExtra("issue_id", issue.issue_id);
                        startActivity(intent);
                    });
                    recyclerView.setAdapter(issueAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Issue>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
