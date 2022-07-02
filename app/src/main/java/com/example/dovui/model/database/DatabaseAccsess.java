package com.example.dovui.model.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.dovui.common.objects.Level;
import com.example.dovui.model.object.Question;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAccsess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccsess  instance;

    /**
     * Private constructor to aboid object creation from outside classes.
     *
     * @param context
     */
    private DatabaseAccsess(Context context) {
        this.openHelper = new DBconfig(context);
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static DatabaseAccsess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccsess(context);
        }
        return instance;
    }

    /**
     * Open the database connection.
     */
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }
    public List<Question> getGetQuestionLevel1() {
        List<Question> questions = new ArrayList<>();
        int limit = 20;
        Cursor cursor = database.rawQuery("SELECT * FROM level1 ORDER BY random() LIMIT " + limit, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String question = cursor.getString(cursor.getColumnIndex("question"));
            String answerA = cursor.getString(cursor.getColumnIndex("answerA"));
            String answerB = cursor.getString(cursor.getColumnIndex("answerB"));
            String answerC = cursor.getString(cursor.getColumnIndex("answerC"));
            String answerD = cursor.getString(cursor.getColumnIndex("answerD"));
            String trueAnswer = cursor.getString(cursor.getColumnIndex("trueAnswer"));
            Question aQuestion = new Question( question, answerA, answerB, answerC, answerD, trueAnswer);
            questions.add(aQuestion);
            cursor.moveToNext();
        }
        cursor.close();
        return questions;
    }
    public List<Question> getGetQuestionLevel2() {
        List<Question> questions = new ArrayList<>();
        int limit = 20;
        Cursor cursor = database.rawQuery("SELECT * FROM level2 ORDER BY random() LIMIT " + limit, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String question = cursor.getString(cursor.getColumnIndex("question"));
            String answerA = cursor.getString(cursor.getColumnIndex("answerA"));
            String answerB = cursor.getString(cursor.getColumnIndex("answerB"));
            String answerC = cursor.getString(cursor.getColumnIndex("answerC"));
            String answerD = cursor.getString(cursor.getColumnIndex("answerD"));
            String trueAnswer = cursor.getString(cursor.getColumnIndex("trueAnswer"));
            Question aQuestion = new Question( question, answerA, answerB, answerC, answerD, trueAnswer);
            questions.add(aQuestion);
            cursor.moveToNext();
        }
        cursor.close();
        return questions;
    }
    public List<Question> getGetQuestionLevel3() {
        List<Question> questions = new ArrayList<>();
        int limit = 20;
        Cursor cursor = database.rawQuery("SELECT * FROM level3 ORDER BY random() LIMIT " + limit, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String question = cursor.getString(cursor.getColumnIndex("question"));
            String answerA = cursor.getString(cursor.getColumnIndex("answerA"));
            String answerB = cursor.getString(cursor.getColumnIndex("answerB"));
            String answerC = cursor.getString(cursor.getColumnIndex("answerC"));
            String answerD = cursor.getString(cursor.getColumnIndex("answerD"));
            String trueAnswer = cursor.getString(cursor.getColumnIndex("trueAnswer"));
            Question aQuestion = new Question( question, answerA, answerB, answerC, answerD, trueAnswer);
            questions.add(aQuestion);
            cursor.moveToNext();
        }
        cursor.close();
        return questions;
    }
}
