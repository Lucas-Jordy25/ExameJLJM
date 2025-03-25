package com.example.examejljm;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class VolumesActivity extends AppCompatActivity implements VolumeAdapter.OnVolumeClickListener {

    private RecyclerView recyclerViewVolumes;
    private VolumeAdapter volumeAdapter;
    private ArrayList<Volumen> volumenList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volumes);

        recyclerViewVolumes = findViewById(R.id.recyclerVolumes);
        recyclerViewVolumes.setLayoutManager(new LinearLayoutManager(this));

        volumenList = new ArrayList<>();
        volumeAdapter = new VolumeAdapter(volumenList, this);
        recyclerViewVolumes.setAdapter(volumeAdapter);

        String journalId = getIntent().getStringExtra("journal_id");
        if (journalId != null) {
            obtenerVolumenes(journalId);
        } else {
            Toast.makeText(this, "Error: journal_id no recibido", Toast.LENGTH_SHORT).show();
        }
    }

    private void obtenerVolumenes(String journalId) {
        String url = "https://revistas.uteq.edu.ec/ws/issues.php?j_id=" + journalId;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject volumenObject = response.getJSONObject(i);
                                String issueId = volumenObject.getString("issue_id");
                                String title = volumenObject.getString("title");
                                volumenList.add(new Volumen(issueId, title));
                            }
                            volumeAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(VolumesActivity.this, "Error al parsear datos", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(VolumesActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                Log.e("VolleyError", error.toString());
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    @Override
    public void onVolumeClick(Volumen volumen) {
        Intent intent = new Intent(this, ArticlesActivity.class);
        intent.putExtra("issue_id", volumen.getIssue_id());
        startActivity(intent);
    }
}
