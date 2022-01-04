package com.example.emargementnfc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ExamSessionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_session);
    }

    public void createNewExamSession(View view) {
        Intent settingsIntent = new Intent(this, CreateExamSessionActivity.class);
        startActivity(settingsIntent);
    }

    public void quit(View view) {
        this.finish();
    }
}