package com.dpoddubko.sqlite_search;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends Activity implements OnClickListener {
    @BindView(R.id.btnGenerate)
    Button btnGenerate;
    private DBHelper dbHelper;
    private ProgressDialog progress;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ButterKnife.bind(this);
        btnGenerate.setOnClickListener(this);
        dbHelper = new DBHelper(this);
    }

    @Override
    public void onClick(View v) {
        progress = ProgressDialog.show(this, "Please wait!",
                "Database is creating", true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                ContentValues cv = new ContentValues();
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.beginTransaction();

                cv.put("name", "combination of names");
                db.insert("category", null, cv);
                cv.clear();

                String[] firstNames = Arrs.getFirstNames();
                String[] lastNames = Arrs.getLastNames();
                for (int i = 0; i < 100_000; i++) {
                    StringBuilder name = new StringBuilder();
                    String firstName = firstNames[randomNum(0, firstNames.length - 1)];
                    String lastName = lastNames[randomNum(0, lastNames.length - 1)];
                    name.append(firstName).append(" ").append(lastName);
                    cv.put("content", name.toString());
                    db.insert("note_content", null, cv);
                }
                cv.clear();

                for (int i = 0; i < 100_000; i++) {
                    String name = "note " + i;
                    cv.put("name", name);
                    cv.put("category_id", 1);
                    cv.put("note_content_id", i);
                    db.insert("note", null, cv);
                }
                cv.clear();
                db.execSQL("INSERT INTO content_search SELECT id, content FROM note_content;");
                db.setTransactionSuccessful();
                db.endTransaction();
                dbHelper.close();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progress.dismiss();
                    }
                });
            }
        }).start();
    }

    private int randomNum(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
}