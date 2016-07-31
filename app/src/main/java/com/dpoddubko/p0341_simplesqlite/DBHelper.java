package com.dpoddubko.p0341_simplesqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DBHelper extends SQLiteOpenHelper {

    private String states = "create table states ("
            + "id_state integer primary key autoincrement, name text);";

    private String employees = "create table employees ("
            + "id_employee integer primary key autoincrement,"
            + "first_name text,"
            + "last_name text,"
            + "id_state integer,"
            + "phone integer,"
            + "sex integer" + ");";

    private String dataEmployees = "create table data_employees ("
            + "id_data_employee integer primary key autoincrement,"
            + "position integer,"
            + "age integer,"
            + "experience integer,"
            + "health integer,"
            + "leadership integer" + ");";

    private String sex = "create table sex ("
            + "id_sex integer primary key autoincrement, name text);";

    private String position = "create table position ("
            + "id_position integer primary key autoincrement, name text);";

    public DBHelper(Context context) {
        // конструктор суперкласса
        super(context, "MegaDev", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(states);
        db.execSQL(employees);
        db.execSQL(dataEmployees);
        db.execSQL(sex);
        db.execSQL(position);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}