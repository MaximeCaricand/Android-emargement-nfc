package com.example.emargementnfc;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.nfc.Tag;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.math.BigInteger;
import java.util.Calendar;

public class ScanActivity extends AppCompatActivity {

    private static final int NEW_STUDENT_CODE = 0;

    NfcAdapter adapter;
    PendingIntent mPendingIntent;
    DBMain dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        Intent intent;
        intent = this.getIntent();

        this.dbHandler = new DBMain(getApplicationContext());

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
    @SuppressLint("MissingSuperCall")
    protected void onNewIntent(Intent intent) {
        this.getTagInfo(intent);
    }

    private void getTagInfo(Intent intent) {
        String uid = new BigInteger(intent.getByteArrayExtra(NfcAdapter.EXTRA_ID)).toString(16);
        Student student = this.dbHandler.getStudent(uid);
        if (student != null) {
            Toast.makeText(this, "Student: " + student.getName(), Toast.LENGTH_LONG).show();
            final Calendar c = Calendar.getInstance();
            String formatedDate = formatDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
            String formatedHour = formatHour(c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE));
            ExamSession currentExamSession = this.dbHandler.getExamSession(formatedDate, formatedHour);
            if (currentExamSession != null){
                if(this.dbHandler.getExamSessionStudent(currentExamSession.getId(), student.getId()) != null) {
                    // update
                    this.dbHandler.updateStudentExamSession(currentExamSession.getId(), student.getId(), formatedHour);
                } else {
                    // add
                    this.dbHandler.addExamSessionStudent(new ExamSessionStudent(currentExamSession.getId(), student.getId(), formatedHour));
                }
            }
        } else {
            Intent highscoresIntent = new Intent(this, NewStudentActivity.class);
            highscoresIntent.putExtra("id", uid);
            startActivityForResult(highscoresIntent, NEW_STUDENT_CODE);
        }
    }

    public void quit(View view) {
        this.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)	{
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case NEW_STUDENT_CODE:
                    String id = data.getStringExtra("id");
                    String name = data.getStringExtra("name");
                    this.dbHandler.addStudent(new Student(id, name));
                    Toast.makeText(this, "Student created ! \n Please pass the badge again", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }

    private static String formatDate(int year, int month, int day) {
        String fullMonth = "" + (month + 1);
        if (month < 10)
            fullMonth = "0" + fullMonth;

        String fullDay = "" + day;
        if (day < 10)
            fullDay = "0" + fullDay;

        return fullDay + "/" + fullMonth + "/" + year;
    }

    private static String formatHour(int hour, int minute) {
        String fullHour = "" + hour;
        if (hour < 10)
            fullHour = "0" + fullHour;

        String fullMinute = "" + minute;
        if (minute < 10)
            fullMinute = "0" + fullMinute;

        return fullHour + ":" + fullMinute;
    }
}