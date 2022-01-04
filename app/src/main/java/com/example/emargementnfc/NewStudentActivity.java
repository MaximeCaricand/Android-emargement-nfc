package com.example.emargementnfc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class NewStudentActivity extends AppCompatActivity {

    private String id;
    private String name;
    private EditText nameInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_student);

        this.id = getIntent().getStringExtra("id");
        this.name = "unknown";
        this.nameInput = findViewById(R.id.student_name);
    }

    public void submit (View v) {
        Intent returnContent = new Intent();
        returnContent.putExtra("id", this.id);
        returnContent.putExtra("name", this.nameInput.getText().toString());
        setResult(RESULT_OK, returnContent);
        this.finish();
    }
}