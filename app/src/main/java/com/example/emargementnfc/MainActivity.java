package com.example.emargementnfc;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    DBMain dbHandler; // delete this, only debug purpose

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.dbHandler = new DBMain(getApplicationContext());

        Student s = new Student("01234", "FLORIAN HENRY");
        Student s2 = new Student("56789", "MAXIME CARICAND");
        dbHandler.addStudent(s);
        dbHandler.addStudent(s2);

        ExamSession es = new ExamSession(-1,"DROIT", "24/11/1998", "12:00", "16:30");
        ExamSession es2 = new ExamSession(-1,"ANDROID", "04/01/2022", "08:30", "12:00");
        dbHandler.addExamSession(es);
        dbHandler.addExamSession(es2);



    }

    public void openScan(View view) {
        Intent settingsIntent = new Intent(this, ScanActivity.class);
        startActivity(settingsIntent);
    }

    public void openExamSessions(View view) {
        Intent settingsIntent = new Intent(this, ExamSessionActivity.class);
        startActivity(settingsIntent);
    }

    public void clearDB(View view) {
        this.dbHandler.clearTable();
    }

}