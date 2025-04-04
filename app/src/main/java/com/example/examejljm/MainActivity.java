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

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RevistaAdapter journalAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.exameJLJM.R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://revistas.uteq.edu.ec/ws/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ServiceA apiService = retrofit.create(ServiceA.class);
        apiService.getJournals().enqueue(new Callback<List<Revista>>() {
            @Override
            public void onResponse(Call<List<Revista>> call, Response<List<Revista>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    journalAdapter = new RevistaAdapter(MainActivity.this, response.body(), journal -> {
                        Intent intent = new Intent(MainActivity.this, ActivityIssue.class);
                        intent.putExtra("journal_id", journal.journal_id);
                        startActivity(intent);
                    });
                    recyclerView.setAdapter(journalAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Revista>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
