package com.dpoddubko.sqlite_search;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DBHelper extends SQLiteOpenHelper {

    private String category = "CREATE TABLE category (\n" +
            " id INTEGER PRIMARY KEY,\n" +
            " name TEXT NOT NULL\n" +
            ");";

    private String note_content = "CREATE TABLE note_content (\n" +
            " id INTEGER PRIMARY KEY,\n" +
            " content TEXT\n" +
            ");";

    private String note = "CREATE TABLE note (\n" +
            " id INTEGER PRIMARY KEY,\n" +
            " name TEXT NOT NULL,\n" +
            " category_id INTEGER NOT NULL,\n" +
            " note_content_id INTEGER NOT NULL UNIQUE,\n" +
            " FOREIGN KEY(category_id) REFERENCES category(id),\n" +
            " FOREIGN KEY(note_content_id) REFERENCES note_content(id)\n" +
            ");";

    private String content_search = "CREATE VIRTUAL TABLE content_search USING fts4(id, content);";

    public DBHelper(Context context) {
        // конструктор суперкласса
        super(context, "MegaDev", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(note_content);
        db.execSQL(category);
        db.execSQL(note);
        db.execSQL(content_search);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.execSQL("PRAGMA foreign_keys=ON;");
    }
}