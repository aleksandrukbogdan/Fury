package com.example.fury;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "users.db"; // название бд
    private static final int SCHEMA = 1; // версия базы данных
    static final String TABLE = "users_table"; // название таблицы в бд
    // названия столбцов
    public static final String user_id = "_id";
    public static final String user_name = "name";
    public static final String user_score = "score";
    public static final String game_over = "lose";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE users_table (" + user_id
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + user_name
                + " TEXT, " + user_score + " INTEGER);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE);
        onCreate(db);
    }
}
