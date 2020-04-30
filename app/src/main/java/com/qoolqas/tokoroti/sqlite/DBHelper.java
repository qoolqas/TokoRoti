package com.qoolqas.tokoroti.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "roti.db";
    public static final int DB_VERSION = 1;

    //ROTI
    public static final String TABLE_ROTI = "tableroti";
    public static final String R_ID = "id";
    public static final String R_KODE = "kode";
    public static final String R_NAMA = "nama";
    public static final String R_DESKRIPSI = "deskripsi";
    public static final String R_HARGA = "harga";
    public static final String R_IMAGE = "image";
    public static final String R_SELECTION = "selection";

    //USER
    public static final String TABLE_USER = "tableuser";
    public static final String USER_ID = "user_id";
    public static final String USER_NAME = "user_name";
    public static final String USER_PASSWORD = "user_password";

    //PEMBELIAN
    public static final String TABLE_BELI = "tablebeli";
    public static final String B_ID = "id";
    public static final String B_KODE = "kode";
    public static final String B_NAMA = "nama";
    public static final String B_HARGA = "harga";
    public static final String B_JUMLAH = "jumlah";
    public static final String B_TOTAL = "total";
    public static final String B_IMAGE = "image";
    public static final String B_SELECTION = "selection";

    private static final String db_roti = "create table "
            + TABLE_ROTI + "("
            + R_ID + " INTEGER primary key autoincrement not null,"
            + R_KODE + " text,"
            + R_NAMA + " text,"
            + R_DESKRIPSI + " text,"
            + R_HARGA + " text,"
            + R_IMAGE + " text,"
            + R_SELECTION + " INTEGER default 0);";

    private static final String db_beli = "create table "
            + TABLE_BELI + "("
            + B_ID + " INTEGER primary key autoincrement not null,"
            + B_KODE + " text,"
            + B_NAMA + " text,"
            + B_HARGA + " text,"
            + B_JUMLAH + " text,"
            + B_TOTAL + " text,"
            + B_IMAGE + " text,"
            + B_SELECTION + " INTEGER default 0);";

    private static final String db_user = "create table "
            + TABLE_USER + "("
            + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + USER_NAME + " TEXT NOT NULL UNIQUE,"
            + USER_PASSWORD + " TEXT);";

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(db_roti);
        sqLiteDatabase.execSQL(db_beli);
        sqLiteDatabase.execSQL(db_user);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, DB_VERSION);
    }
}
