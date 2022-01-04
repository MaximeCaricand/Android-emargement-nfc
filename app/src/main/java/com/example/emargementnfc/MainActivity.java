package com.example.emargementnfc;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.nfc.Tag;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.math.BigInteger;


public class MainActivity extends AppCompatActivity {
    NfcAdapter adapter;
    PendingIntent mPendingIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent;
        intent = this.getIntent();

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

    @Override
    protected void onNewIntent(Intent intent) {
        getTagInfo(intent);
    }

    private void getTagInfo(Intent intent) {
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        byte[] uid = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID);

        String s = new BigInteger(uid).toString(16);
        afficherMessage(s);

    }

    public void nouvelleCarte(View v){
        Intent intentGame = new Intent(this, NouvelleCarte.class);
        startActivityForResult(intentGame,666);
    }

    public void onActivityResult(int code, int status, Intent res) {
        super.onActivityResult(code, status, res);

        /*switch (code){
            case 666:
                if(status == RESULT_OK){
                    userName = res.getStringExtra("userName");
                    prey = res.getStringExtra("prey");
                    killer = res.getStringExtra("killer");
                    score = res.getIntExtra("score",-1);
                    date = res.getStringExtra("date");
                    tvScore.setText("Last Killing Spree : " + score + " kills");
                }
                break;
        }*/
    }

    public void carteExistante(View v){
    }

    public void afficherMessage(String s) {

        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }
}