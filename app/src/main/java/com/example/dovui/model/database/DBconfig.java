package com.example.dovui.model.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DBconfig extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "tracnghiem_dovui3.db";
    private static final int DATABASE_VERSION = 1;

    public DBconfig(Context context) {
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
    }
}
