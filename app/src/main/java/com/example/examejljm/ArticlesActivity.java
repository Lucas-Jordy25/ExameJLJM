package com.example.examejljm;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.exameJLJM.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class ArticlesActivity extends AppCompatActivity {

    private RecyclerView recyclerArticles;
    private ArticleAdapter articleAdapter;
    private ArrayList<Articulo> articuloList;
    private RequestQueue requestQueue;
    private String issueId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);

        recyclerArticles = findViewById(com.example.exameJLJM.R.id.recyclerArticulos);
        recyclerArticles.setLayoutManager(new LinearLayoutManager(this));

        articuloList = new ArrayList<>();
        articleAdapter = new ArticleAdapter(articuloList, this);
        recyclerArticles.setAdapter(articleAdapter);

        issueId = getIntent().getStringExtra("issue_id");
        requestQueue = Volley.newRequestQueue(this);

        obtenerArticulos(issueId);
    }

    private void obtenerArticulos(String issueId) {
        String url = "https://revistas.uteq.edu.ec/ws/pubs.php?i_id=" + issueId;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject articleJson = response.getJSONObject(i);

                                String title = articleJson.getString("title");
                                String doi = articleJson.getString("doi");

                                articuloList.add(new Articulo(title, doi));
                            }
                            articleAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ArticlesActivity.this, "Error cargando artículos", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(request);
    }
}
