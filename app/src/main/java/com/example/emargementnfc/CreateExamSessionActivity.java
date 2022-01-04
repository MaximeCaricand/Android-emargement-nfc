package com.example.emargementnfc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class CreateExamSessionActivity extends AppCompatActivity {

    private EditText nameInput;
    private TextView dateTv;
    private TextView startHourTv;
    private TextView endHourTv;

    private DBMain dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_exam_session);

        this.dbHandler = new DBMain(getApplicationContext());

        this.nameInput = findViewById(R.id.username);
        this.dateTv = findViewById(R.id.date);
        this.startHourTv = findViewById(R.id.startHour);
        this.endHourTv = findViewById(R.id.endHour);
    }

    public void showDatePicker(View v) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String fullMonth = "" + (month + 1);
                if (month < 10)
                    fullMonth = "0" + fullMonth;

                String fullDay = "" + dayOfMonth;
                if (dayOfMonth < 10)
                    fullDay = "0" + fullDay;

                dateTv.setText(fullDay + "/" + fullMonth + "/" + year);
            }
        };
        DatePickerDialog picker = new DatePickerDialog(this, dateSetListener,  year, month, day);
        picker.show();
    }

    public void showStartTimePicker(View v) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hour, int minute) {
                String startTime = minute + ":" + hour;
                if (minute < 10)
                    startTime = "0" + startTime;
                startHourTv.setText(startTime);
            }
        };
        TimePickerDialog picker = new TimePickerDialog(this, timeSetListener,  hour, minute, true);
        picker.show();
    }

    public void showEndTimePicker(View v) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hour, int minute) {
                String endTime = minute + ":" + hour;
                if (minute < 10)
                    endTime = "0" + endTime;
                endHourTv.setText(endTime);
            }
        };
        TimePickerDialog picker = new TimePickerDialog(this, timeSetListener,  hour, minute, true);
        picker.show();
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putString("name", nameInput.getText().toString());
        bundle.putString("date", dateTv.getText().toString());
        bundle.putString("startHour", startHourTv.getText().toString());
        bundle.putString("endHour", endHourTv.getText().toString());
    }

    public void onRestoreInstanceState(Bundle bundle) {
        nameInput.setText(bundle.getString("name"));
        dateTv.setText(bundle.getString("date"));
        startHourTv.setText(bundle.getString("startHour"));
        endHourTv.setText(bundle.getString("endHour"));
    }

    public void submit(View v) {
        System.out.println(nameInput.getText());
        System.out.println(dateTv.getText());
        System.out.println(startHourTv.getText());
        System.out.println(endHourTv.getText());
        this.finish();
    }
}