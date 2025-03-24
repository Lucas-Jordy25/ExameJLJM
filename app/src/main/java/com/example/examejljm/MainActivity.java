package com.example.examejljm;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RevistaAdapter revistaAdapter;
    private List<Revista> revistaList;
    private static final String URL = "https://revistas.uteq.edu.ec/ws/journals.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(com.example.exameJLJM.R.id.recyclerRevistas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        revistaList = new ArrayList<>();
        revistaAdapter = new RevistaAdapter(revistaList, this);
        recyclerView.setAdapter(revistaAdapter);

        loadRevistas();
    }

    private void loadRevistas() {
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject revistaObject = response.getJSONObject(i);

                                String title = revistaObject.getString("name");
                                String portada = revistaObject.getString("portada");

                                revistaList.add(new Revista(title, portada));
                            }
                            revistaAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Error al procesar datos", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error al obtener datos", Toast.LENGTH_SHORT).show();
                Log.e("VolleyError", error.toString());
            }
        });

        queue.add(jsonArrayRequest);
    }
}
