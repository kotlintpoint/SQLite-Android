package com.example.admin.sqlitedemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Admin on 9/20/2017.
 */

public class KotlinSqliteOpenHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="kotlindb";
    public static final int VERSION=1;

    public static final String TABLE_PERSON="person";
    public static final String COL_ID="id";
    public static final String COL_FIRST_NAME="first_name";
    public static final String COL_LAST_NAME="last_name";
    public static final String COL_MOBILE="mobile";

    public static final String CREATE_PERSON="create table "+TABLE_PERSON+" ("
            +COL_ID+" integer primary key autoincrement, "
            +COL_FIRST_NAME+" TEXT, "+COL_LAST_NAME+" TEXT, "+COL_MOBILE+" TEXT)";


    public KotlinSqliteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_PERSON);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}
