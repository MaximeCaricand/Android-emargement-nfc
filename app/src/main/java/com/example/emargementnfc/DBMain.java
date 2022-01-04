package com.example.emargementnfc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBMain  extends SQLiteOpenHelper {

    private static final String DBNAME = "emargementnfc";
    private static final String STUDENT_TABLE = "student";
    private static final int VER = 2;

    // student table fields name
    private static final String STUDENT_KEY_ID = "id";
    private static final String STUDENT_KEY_NAME = "name";

    public DBMain(@Nullable Context context) {
        super(context, DBNAME, null, VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + STUDENT_TABLE + "(" + STUDENT_KEY_ID + " TEXT PRIMARY KEY," + STUDENT_KEY_NAME + " TEXT"+ ")";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("DatabaseHandler", "upgrading database");
        db.execSQL("drop table if exists " + STUDENT_TABLE + "");
        onCreate(db);
    }

    public long addStudent(Student contact) {
        long insertId = -1;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(STUDENT_KEY_ID, contact.getId());
        values.put(STUDENT_KEY_NAME, contact.getName());

        // Inserting Row
        insertId = db.insert(STUDENT_TABLE, null, values);
        db.close(); // Closing database connection
        return insertId;
    }

    public Student getStudent(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(STUDENT_TABLE,
                new String[] {STUDENT_KEY_ID, STUDENT_KEY_NAME },
                STUDENT_KEY_ID + "=?",
                new String[] {String.valueOf(id)}, null, null, null, null);

        Student student = null;

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            student = new Student(cursor.getString(0), cursor.getString(1));
        }

        cursor.close();
        db.close();
        return student;
    }

    public void clearTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("drop table if exists " + STUDENT_TABLE + "");
        db.execSQL("CREATE TABLE " + STUDENT_TABLE + "(" + STUDENT_KEY_ID + " TEXT PRIMARY KEY," + STUDENT_KEY_NAME + " TEXT"+ ")");
        db.close();
    }
}
