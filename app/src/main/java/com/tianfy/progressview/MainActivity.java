package com.tianfy.progressview;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.tianfy.progressview.R;

public class MainActivity extends AppCompatActivity {

    private ProgressView progressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressView = findViewById(R.id.progress_view);
        findViewById(R.id.btn_startAnim).setOnClickListener(v -> startAnim());
        findViewById(R.id.btn_endAnim).setOnClickListener(v -> endAnim());
        findViewById(R.id.btn_cancelAnim).setOnClickListener(v -> cancelAnim());
    }

    private void cancelAnim() {
        progressView.cancelAnim();
    }

    private void endAnim() {
        progressView.endAnim();
    }

    private void startAnim() {
        progressView.startAnim();
    }
}