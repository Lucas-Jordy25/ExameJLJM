package com.example.examejljm;
import android.os.Bundle;
import android.webkit.WebView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.exameJLJM.R;

public class ArticledetailA extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.exameJLJM.R.layout.activitydetail);

        String doi = getIntent().getStringExtra("doi");
        WebView webView = findViewById(R.id.webView);
        webView.loadUrl(doi);
    }
}
