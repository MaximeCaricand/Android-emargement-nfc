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
    private static final String EXAMSESSION_TABLE = "examsession";
    private static final int VER = 2;

    // student table fields name
    private static final String STUDENT_KEY_ID = "id";
    private static final String STUDENT_KEY_NAME = "name";
    private String createStudent = "CREATE TABLE " + STUDENT_TABLE +
            "(" + STUDENT_KEY_ID + " TEXT PRIMARY KEY,"
                + STUDENT_KEY_NAME + " TEXT"+
            ")";

    //exam session fiels name
    private static final String EXAMSESSION_KEY_ID = "id";
    private static final String EXAMSESSION_KEY_NAME = "name";
    private static final String EXAMSESSION_KEY_STARTHOUR = "starthour";
    private static final String EXAMSESSION_KEY_ENDHOUR = "endhour";
    private String createExamSession = "CREATE TABLE " + EXAMSESSION_TABLE +
            "(" + EXAMSESSION_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + EXAMSESSION_KEY_NAME + " TEXT,"
                + EXAMSESSION_KEY_STARTHOUR +"TEXT,"
                + EXAMSESSION_KEY_ENDHOUR + "TEXT" +
            ")";

    public DBMain(@Nullable Context context) {
        super(context, DBNAME, null, VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createStudent);
        db.execSQL(createExamSession);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("DatabaseHandler", "upgrading database");
        db.execSQL("drop table if exists " + STUDENT_TABLE + "");
        db.execSQL("drop table if exists " + EXAMSESSION_TABLE + "");
        onCreate(db);
    }


    //Student
    //ajout d'un etudiant
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

    //Récupérer un étudiant précis
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

    //Exam Session
    //ajout d'un exam
    public long addExamSession(ExamSession eS){
        long insertId = -1;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EXAMSESSION_KEY_ID, eS.getId());
        values.put(EXAMSESSION_KEY_NAME, eS.getName());
        values.put(EXAMSESSION_KEY_STARTHOUR, eS.getStartHour());
        values.put(EXAMSESSION_KEY_ENDHOUR, eS.getEndHour());

        // Inserting Row
        insertId = db.insert(EXAMSESSION_TABLE, null, values);
        db.close(); // Closing database connection
        return insertId;
    }

    public ExamSession getExamSession(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(EXAMSESSION_TABLE,
                new String[] {EXAMSESSION_KEY_ID, EXAMSESSION_KEY_NAME,EXAMSESSION_KEY_STARTHOUR,EXAMSESSION_KEY_ENDHOUR },
                EXAMSESSION_KEY_ID + "=?",
                new String[] {String.valueOf(id)}, null, null, null, null);

        ExamSession eS = null;

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            eS = new ExamSession(cursor.getInt(0), cursor.getString(1),cursor.getString(2), cursor.getString(3));
        }

        cursor.close();
        db.close();
        return eS;
    }

    public void clearTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("drop table if exists " + STUDENT_TABLE + "");
        db.execSQL("drop table if exists " + EXAMSESSION_TABLE + "");
        db.execSQL(createStudent);
        db.execSQL(createExamSession);
        db.close();
    }
}
