package com.example.examejljm;
import android.content.Intent;
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

public class VolumesActivity extends AppCompatActivity implements VolumeAdapter.OnVolumeClickListener {

    private RecyclerView recyclerVolumes;
    private VolumeAdapter volumeAdapter;
    private ArrayList<Volumen> volumenList;
    private RequestQueue requestQueue;
    private String journalId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volumes);

        recyclerVolumes = findViewById(com.example.exameJLJM.R.id.recyclerVolumes);
        recyclerVolumes.setLayoutManager(new LinearLayoutManager(this));

        volumenList = new ArrayList<>();
        volumeAdapter = new VolumeAdapter(this, volumenList, this);
        recyclerVolumes.setAdapter(volumeAdapter);

        journalId = getIntent().getStringExtra("journal_id");
        requestQueue = Volley.newRequestQueue(this);

        obtenerVolumenes(journalId);
    }

    private void obtenerVolumenes(String journalId) {
        String url = "https://revistas.uteq.edu.ec/ws/issues.php?j_id=" + journalId;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray issuesArray = response.getJSONArray("issues");

                            for (int i = 0; i < issuesArray.length(); i++) {
                                JSONObject issueJson = issuesArray.getJSONObject(i);

                                String issue_id = issueJson.getString("issue_id");
                                String title = issueJson.getString("title");

                                volumenList.add(new Volumen(issue_id, title));
                            }
                            volumeAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(VolumesActivity.this, "Error cargando volúmenes", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(request);
    }

    @Override
    public void onVolumeClick(String issueId) {
        Intent intent = new Intent(VolumesActivity.this, ArticlesActivity.class);
        intent.putExtra("issue_id", issueId);
        startActivity(intent);
    }
}
