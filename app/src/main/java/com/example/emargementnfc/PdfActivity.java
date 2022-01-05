package com.example.emargementnfc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.pdf.PdfDocument;
import android.graphics.pdf.PdfRenderer;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.Manifest.permission.MANAGE_EXTERNAL_STORAGE;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.Inflater;

public class PdfActivity extends AppCompatActivity {

    int pageHeight = 1120;
    int pagewidth = 792;
    int currentPage = 0;

    private PdfDocument pdfDocument;
    private ImageView view_pdf;
    private Button btn_generate;

    private PdfRenderer renderer;
    private PdfRenderer.Page rendererPage;

    private static final int PERMISSION_REQUEST_CODE = 200;
    private static final String FILE_NAME = "monsuperpdf.pdf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);

        view_pdf = findViewById(R.id.view_pdf);
        btn_generate = findViewById(R.id.pdf);

        createPdf();
        showPage(currentPage);

        if (checkPermission()) {
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
        } else {
            requestPermission();
        }
    }

    public void createPdf() {
        pdfDocument = new PdfDocument();
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

        downloadPdf(getApplication().getCacheDir().getPath());
    }

    public void showPage(int index) {
        try {
            renderer = new PdfRenderer(ParcelFileDescriptor.open(
                readFile(), ParcelFileDescriptor.MODE_READ_ONLY
            ));
            if (null != rendererPage) {
                rendererPage.close();
            }
            rendererPage = renderer.openPage(index);

            Bitmap bitmap = Bitmap.createBitmap(
                rendererPage.getWidth(),
                rendererPage.getHeight(),
                Bitmap.Config.ARGB_4444
            );

            view_pdf.setImageBitmap(bitmap);
            rendererPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
            rendererPage.close();
            renderer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void next(View v) {
        currentPage = (currentPage + 1) % renderer.getPageCount();
        showPage(currentPage);
    }

    public void previous(View v) {
        currentPage = (currentPage - 1) % renderer.getPageCount();
        showPage(currentPage);
    }

    private File readFile() {
        File file = null;
        try {
            file = new File(getApplication().getCacheDir().getPath(), FILE_NAME);
            FileInputStream is = new FileInputStream(file);

            // Get the size of the file
            long length = file.length();
            byte[] bytes = new byte[(int) length];
            int offset = 0;
            int numRead = 0;
            while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
                offset += numRead;
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return file;
    }


    public void downloadPdf(View v) {
        downloadPdf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString());
    }

    public void downloadPdf(String path) {
        File outDir = new File(path, FILE_NAME);
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