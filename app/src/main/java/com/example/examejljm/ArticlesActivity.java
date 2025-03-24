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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.exameJLJM.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class ArticlesActivity extends AppCompatActivity implements ArticleAdapter.OnArticleClickListener {

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
        articleAdapter = new ArticleAdapter(this, articuloList, this);
        recyclerArticles.setAdapter(articleAdapter);

        issueId = getIntent().getStringExtra("issue_id");
        requestQueue = Volley.newRequestQueue(this);

        obtenerArticulos(issueId);
    }

    private void obtenerArticulos(String issueId) {
        String url = "https://revistas.uteq.edu.ec/ws/pubs.php?i_id=" + issueId;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray articlesArray = response.getJSONArray("articles");

                            for (int i = 0; i < articlesArray.length(); i++) {
                                JSONObject articleJson = articlesArray.getJSONObject(i);

                                String title = articleJson.getString("title");
                                String doi = articleJson.getString("doi");

                                articuloList.add(new Articulo(title,doi));
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

    @Override
    public void onArticleClick(String doi) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(doi));
        startActivity(intent);
    }
}
