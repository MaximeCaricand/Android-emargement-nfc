package com.example.emargementnfc;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DBMain dbHandler; // delete this, only debug purpose

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.dbHandler = new DBMain(getApplicationContext());



        //DEBUG
        /*Student Florian = new Student("zhzve7"     , "Florian HENRY");
        Student Maxime  = new Student("5454azezae" , "Maxime CARICAND");
        Student Camille = new Student("545erda7c"  , "Camille CASTER");
        Student Nicolas = new Student("9z65czczc12", "Nicolas CRUVEILHER");
        Student Alexis  = new Student("pluy854uj"  , "Alexis LABBE");

        ExamSession es = new ExamSession("ANDROID", "05/01/2022", "00:00", "12:00");


        this.dbHandler.addStudent(Florian);
        this.dbHandler.addStudent(Maxime);
        this.dbHandler.addStudent(Camille);
        this.dbHandler.addStudent(Nicolas);
        this.dbHandler.addStudent(Alexis);

        this.dbHandler.addExamSession(es);

        Toast.makeText(this, "ID DE LA SESSION : " + es.getId(), Toast.LENGTH_SHORT).show();

        es = this.dbHandler.getExamSession("05/01/2022", "00:00"); //necessaire pour optenir un ID de session

        Toast.makeText(this, "ID DE LA SESSION : " + es.getId(), Toast.LENGTH_SHORT).show();

        this.dbHandler.addExamSessionStudent(new ExamSessionStudent(es.getId(), Florian.getId(), "01:00", "05:48"));
        this.dbHandler.addExamSessionStudent(new ExamSessionStudent(es.getId(), Maxime.getId() , "00:00", "03:13"));
        this.dbHandler.addExamSessionStudent(new ExamSessionStudent(es.getId(), Camille.getId(), "00:10", "02:54"));
        this.dbHandler.addExamSessionStudent(new ExamSessionStudent(es.getId(), Nicolas.getId(), "00:45", "02:25"));
        this.dbHandler.addExamSessionStudent(new ExamSessionStudent(es.getId(), Alexis.getId() , "00:10", "02:25"));*/
        //for(int i=0; i<50; i++)
          //  this.dbHandler.addExamSessionStudent(new ExamSessionStudent(es.getId(), new Student(""+(Math.random()*20000), ""+(Math.random()*20000)).getId(), "08:00", "12:00"));


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