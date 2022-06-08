package com.example.dailyemotion;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class OpenHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "DB.db";
    private static final String TABLE_NAME_1 = "behavior_db";
    private static final String TABLE_NAME_2 = "emotion_db";
    private static final String SQL_CREATE_ENTRIES_1 =
            "CREATE TABLE " + TABLE_NAME_1 + " (" +
                    "_id" + " INTEGER PRIMARY KEY," +
                    "timeSaved" + " TEXT," +
                    "behavior" + " TEXT," +
                    "startEnd" + " TEXT," +
                    "time" + " TEXT)";
    private static final String SQL_CREATE_ENTRIES_2 =
            "CREATE TABLE " + TABLE_NAME_2 + " (" +
                    "_id" + " INTEGER PRIMARY KEY," +
                    "timeSaved" + " TEXT," +
                    "place" + " TEXT," +
                    "fatigue" + " TEXT," +
                    "upset" + " TEXT," +
                    "hostile" + " TEXT," +
                    "alert" + " TEXT," +
                    "ashamed" + " TEXT," +
                    "inspired" + " TEXT," +
                    "nervous" + " TEXT," +
                    "determined" + " TEXT," +
                    "attentive" + " TEXT," +
                    "afraid" + " TEXT," +
                    "active" + " TEXT," +
                    "valence" + " TEXT," +
                    "arousal" + " TEXT)";
    private static final String SQL_DELETE_ENTRIES_1 =
            "DROP TABLE IF EXISTS " + TABLE_NAME_1;
    private static final String SQL_DELETE_ENTRIES_2 =
            "DROP TABLE IF EXISTS " + TABLE_NAME_2;

    OpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES_1);
        db.execSQL(SQL_CREATE_ENTRIES_2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(
                SQL_DELETE_ENTRIES_1
        );
        db.execSQL(
                SQL_DELETE_ENTRIES_2
        );
        onCreate(db);
    }
}
