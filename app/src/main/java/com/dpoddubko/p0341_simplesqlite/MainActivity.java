package com.dpoddubko.p0341_simplesqlite;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import java.util.Random;

public class MainActivity extends Activity implements OnClickListener {

    Button btnAdd;
    DBHelper dbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);
        dbHelper = new DBHelper(this);
    }


    @Override
    public void onClick(View v) {

        ContentValues cv = new ContentValues();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();
        String[] states = Arrs.getStates();
        for (int i = 0; i < states.length; i++) {
            cv.put("name", states[i]);
            db.insert("states", null, cv);
        }
        cv.clear();
        String[] firstNames = Arrs.getFirstNames();
        String[] lastNames = Arrs.getLastNames();
        for (int i = 0; i < 40_000; i++) {
            cv.put("first_name", firstNames[randomNum(0, firstNames.length - 1)]);
            cv.put("last_name", lastNames[randomNum(0, lastNames.length - 1)]);
            cv.put("id_state", randomNum(1, 29));
            cv.put("phone", randomNum(10000, 99999));
            cv.put("sex", randomNum(1, 2));
            db.insert("employees", null, cv);
        }
        cv.clear();
        String[] positions = Arrs.getPositions();
        for (int i = 0; i < 40_000; i++) {
            cv.put("position", randomNum(1, positions.length - 1));
            cv.put("age", randomNum(20, 50));
            cv.put("experience", randomNum(1, 8));
            cv.put("health", randomNum(1, 10));
            cv.put("leadership", randomNum(1, 10));
            db.insert("data_employees", null, cv);
        }
        cv.clear();
        cv.put("name", "male");
        db.insert("sex", null, cv);
        cv.put("name", "female");
        db.insert("sex", null, cv);
        cv.clear();
        for (int i = 0; i < positions.length; i++) {
            cv.put("name", positions[i]);
            db.insert("position", null, cv);
        }
        cv.clear();
        db.setTransactionSuccessful();
        db.endTransaction();
        dbHelper.close();
    }

    private int randomNum(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
}