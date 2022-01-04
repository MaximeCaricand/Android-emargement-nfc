package com.example.emargementnfc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.Manifest.permission.MANAGE_EXTERNAL_STORAGE;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PdfActivity extends AppCompatActivity {

    Button generatePDFbtn;
    int pageHeight = 1120;
    int pagewidth = 792;

    private static final int PERMISSION_REQUEST_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);

        generatePDFbtn = findViewById(R.id.pdf);


        if (checkPermission()) {
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
        } else {
            requestPermission();
        }
    }

    public void createPdf(View v) {
        PdfDocument pdfDocument = new PdfDocument();
        Paint myPaint = new Paint();

        PdfDocument.PageInfo mypageInfo = new PdfDocument.PageInfo.Builder(pagewidth, pageHeight, 1).create();
        PdfDocument.Page myPage = pdfDocument.startPage(mypageInfo);

        Canvas canvas = myPage.getCanvas();

        myPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        myPaint.setTextSize(15);
        myPaint.setColor(ContextCompat.getColor(this, R.color.purple_200));

        canvas.drawText("A portal for IT professionals.", 209, 100, myPaint);
        canvas.drawText("Geeks for Geeks", 209, 80, myPaint);

        myPaint.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        myPaint.setColor(ContextCompat.getColor(this, R.color.purple_200));
        myPaint.setTextSize(15);

        myPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("This is sample document which we have created.", 396, 560, myPaint);

        pdfDocument.finishPage(myPage);

        //File file = new File(Environment.getExternalStorageDirectory(), "GFG.pdf");
        String myFilePath = Environment.getDataDirectory().getPath() + "/myPDFFile.pdf";
        File file = new File(this.getFilesDir() + "/","sample.pdf");
        File outDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString(), "monsuperpdf.pdf");
        try {
            pdfDocument.writeTo(new FileOutputStream(outDir));
            Toast.makeText(PdfActivity.this, "PDF file generated successfully.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(PdfActivity.this, "HAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA "+ e, Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        pdfDocument.close();
    }

    private boolean checkPermission() {
        int permission1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int permission2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        int permission3 = ContextCompat.checkSelfPermission(getApplicationContext(), MANAGE_EXTERNAL_STORAGE);

        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED && permission3 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE, MANAGE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {
                boolean writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                boolean manage = grantResults[2] == PackageManager.PERMISSION_GRANTED;

                if (writeStorage && readStorage ) {
                    Toast.makeText(this, "Permission Granted..", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission Denined.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }
}