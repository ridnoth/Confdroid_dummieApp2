package com.example.glantz.dummie2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Glantz on 2017-04-12.
 */
public class databasehandler extends SQLiteOpenHelper {

    private String Create_database = "CREATE TABLE user( userName_email TEXT ,name TEXT ,password TEXT);";

    public databasehandler(Context context) {
        super(context, "dbtest", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Create_database);
        System.out.println("Database created!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
