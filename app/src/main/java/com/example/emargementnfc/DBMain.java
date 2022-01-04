package com.example.emargementnfc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBMain extends SQLiteOpenHelper {

    private static final String DBNAME = "emargementnfc";
    private static final String STUDENT_TABLE = "student";
    private static final String EXAMSESSION_TABLE = "examsession";
    private static final String EXAMSESSIONSTUDENT_TABLE = "examsessionstudent";
    private static final int VER = 3; //numero de version

    // student table fields name
    private static final String STUDENT_KEY_ID = "id";
    private static final String STUDENT_KEY_NAME = "name";
    private String createStudent = "CREATE TABLE " + STUDENT_TABLE +
            "(" + STUDENT_KEY_ID + " TEXT PRIMARY KEY,"
                + STUDENT_KEY_NAME + " TEXT"+
            ")";

    // exam session fields name
    private static final String EXAMSESSION_KEY_ID = "id";
    private static final String EXAMSESSION_KEY_NAME = "name";
    private static final String EXAMSESSION_KEY_DATE = "date";
    private static final String EXAMSESSION_KEY_STARTHOUR = "starthour";
    private static final String EXAMSESSION_KEY_ENDHOUR = "endhour";
    private String createExamSession = "CREATE TABLE " + EXAMSESSION_TABLE +
            "(" + EXAMSESSION_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + EXAMSESSION_KEY_NAME + " TEXT,"
                + EXAMSESSION_KEY_DATE + " TEXT,"
                + EXAMSESSION_KEY_STARTHOUR +" TEXT,"
                + EXAMSESSION_KEY_ENDHOUR + " TEXT" +
            ")";

    // exam session student fields name (associative table)
    private static final String EXAMSESSIONSTUDENT_KEY_ID_EXAMSESSION = "id_examsession";
    private static final String EXAMSESSIONSTUDENT_KEY_ID_STUDENT = "id_student";
    private static final String EXAMSESSIONSTUDENT_KEY_ARRIVEHOUR = "arrivehour";
    private static final String EXAMSESSIONSTUDENT_KEY_QUITHOUR = "quithour";
    private String createExamSessionStudent = "CREATE TABLE " + EXAMSESSIONSTUDENT_TABLE +
            "(" + EXAMSESSIONSTUDENT_KEY_ID_EXAMSESSION + " INTEGER,"
            + EXAMSESSIONSTUDENT_KEY_ID_STUDENT + " INTEGER,"
            + EXAMSESSIONSTUDENT_KEY_ARRIVEHOUR +" TEXT,"
            + EXAMSESSIONSTUDENT_KEY_QUITHOUR + " TEXT,"
            + "PRIMARY KEY (" + EXAMSESSIONSTUDENT_KEY_ID_EXAMSESSION + ", " + EXAMSESSIONSTUDENT_KEY_ID_STUDENT + ")" +
            ")";

    public DBMain(@Nullable Context context) {
        super(context, DBNAME, null, VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("DatabaseHandler", "upgrading database");
        dropTable(db);
        createTable(db);
    }

    public void clearTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        dropTable(db);
        createTable(db);
        db.close();
    }

    private void createTable(SQLiteDatabase db) {
        db.execSQL(createStudent);
        db.execSQL(createExamSession);
        db.execSQL(createExamSessionStudent);
    }
    private void dropTable(SQLiteDatabase db) {
        db.execSQL("drop table if exists " + STUDENT_TABLE + "");
        db.execSQL("drop table if exists " + EXAMSESSION_TABLE + "");
        db.execSQL("drop table if exists " + EXAMSESSIONSTUDENT_TABLE + "");
    }

    ///////Student
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

    //récupérer un étudiant précis
    public Student getStudent(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(STUDENT_TABLE,
                new String[] {STUDENT_KEY_ID, STUDENT_KEY_NAME},
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



    ///////Exam Session
    //ajout d'un exam
    public long addExamSession(ExamSession eS){
        long insertId = -1;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EXAMSESSION_KEY_NAME, eS.getName());
        values.put(EXAMSESSION_KEY_DATE, eS.getDate());
        values.put(EXAMSESSION_KEY_STARTHOUR, eS.getStartHour());
        values.put(EXAMSESSION_KEY_ENDHOUR, eS.getEndHour());

        // Inserting Row
        insertId = db.insert(EXAMSESSION_TABLE, null, values);
        db.close(); // Closing database connection
        return insertId;
    }

    //récupérer tous les ids des exams
    public ExamSession getAllExamSession() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(EXAMSESSION_TABLE,
                new String[] {EXAMSESSION_KEY_ID, EXAMSESSION_KEY_NAME, EXAMSESSION_KEY_DATE, EXAMSESSION_KEY_STARTHOUR, EXAMSESSION_KEY_ENDHOUR},
                null, null, null, null, null, null);

        ExamSession eS = null;

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            eS = new ExamSession(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
        }

        cursor.close();
        db.close();
        return eS;
    }

    //récupérer un exam précis
    public ExamSession getExamSession(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(EXAMSESSION_TABLE,
                new String[] {EXAMSESSION_KEY_ID, EXAMSESSION_KEY_NAME, EXAMSESSION_KEY_DATE, EXAMSESSION_KEY_STARTHOUR, EXAMSESSION_KEY_ENDHOUR},
                EXAMSESSION_KEY_ID + "=?",
                new String[] {String.valueOf(id)}, null, null, null, null);

        ExamSession eS = null;

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            eS = new ExamSession(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
        }

        cursor.close();
        db.close();
        return eS;
    }



    ///////Student Exam Session
    //ajout d'une relation
    public long addStudentExamSession(ExamSession eS){
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

    //récupérer un relation précis
    public ExamSessionStudent getStudentExamSession(String id_examsession) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(EXAMSESSIONSTUDENT_TABLE,
                new String[] {EXAMSESSIONSTUDENT_KEY_ID_EXAMSESSION, EXAMSESSIONSTUDENT_KEY_ID_STUDENT, EXAMSESSIONSTUDENT_KEY_ARRIVEHOUR, EXAMSESSIONSTUDENT_KEY_QUITHOUR},
                EXAMSESSIONSTUDENT_KEY_ID_EXAMSESSION + "=?",
                new String[] {String.valueOf(id_examsession)}, null, null, null, null);

        ExamSessionStudent eSS = null;

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            eSS = new ExamSessionStudent(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getString(3));
        }

        cursor.close();
        db.close();
        return eSS;
    }
}
