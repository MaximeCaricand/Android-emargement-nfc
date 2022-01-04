package com.example.emargementnfc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    DBMain dbHandler; // delete this, only debug purpose

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.dbHandler = new DBMain(getApplicationContext());
    }

    public void openScan(View view)	{
        Intent settingsIntent = new Intent(this, ScanActivity.class);
        startActivity(settingsIntent);
    }

    public void clearDB(View view)	{
        this.dbHandler.clearTable();
    }

}