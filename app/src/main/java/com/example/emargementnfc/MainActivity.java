package com.example.emargementnfc;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.nfc.Tag;
import android.nfc.tech.MifareUltralight;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.Charset;


public class MainActivity extends AppCompatActivity {
    NfcAdapter adapter;
    PendingIntent mPendingIntent;
    private EditText username;

    DBMain dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent;
        intent = this.getIntent();

        this.dbHandler = new DBMain(getApplicationContext());
        this.username = findViewById(R.id.username);

        NfcManager manager = (NfcManager) this.getSystemService(Context.NFC_SERVICE);
        adapter = manager.getDefaultAdapter();

        if (adapter != null) {

            if (adapter.isEnabled()) {
                if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
                    Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                }
            }
            mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
                    getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.enableForegroundDispatch(this, mPendingIntent, null, null);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (adapter != null) {
            adapter.disableForegroundDispatch(this);
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onNewIntent(Intent intent) {
        getTagInfo(intent);
    }

    private void getTagInfo(Intent intent) {
        String uid = new BigInteger(intent.getByteArrayExtra(NfcAdapter.EXTRA_ID)).toString(16);
        Student student = this.dbHandler.getStudent(uid);
        if (student != null)
            Toast.makeText(this, student.getName(), Toast.LENGTH_LONG).show();
        else {
            String username = this.username.getText().toString();
            this.dbHandler.addStudent(new Student(uid, username));
            Toast.makeText(this, uid, Toast.LENGTH_LONG).show();
        }
    }

    public void clearDB(View view)	{
        this.dbHandler.clearTable();
    }
}