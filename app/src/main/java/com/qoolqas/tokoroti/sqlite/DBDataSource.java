package com.qoolqas.tokoroti.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


import com.qoolqas.tokoroti.pojo.DataBeli;
import com.qoolqas.tokoroti.pojo.DataRoti;
import com.qoolqas.tokoroti.pojo.DataUser;

import java.util.ArrayList;
import java.util.List;

public class DBDataSource {
    private SQLiteDatabase database;
    private DBHelper dbHelper;

    private String[] rotidata = new String[]
            {
                    DBHelper.R_KODE,
                    DBHelper.R_NAMA,
                    DBHelper.R_DESKRIPSI,
                    DBHelper.R_HARGA,
                    DBHelper.R_IMAGE,
                    DBHelper.R_SELECTION

            };
    private String[] belidata = new String[]
            {
                    DBHelper.B_KODE,
                    DBHelper.B_NAMA,
                    DBHelper.B_HARGA,
                    DBHelper.B_JUMLAH,
                    DBHelper.B_TOTAL,
                    DBHelper.B_IMAGE,
                    DBHelper.B_SELECTION
            };
    private String[] userData = new String[]
            {
                    DBHelper.USER_NAME,
                    DBHelper.USER_PASSWORD
            };
    public DBDataSource(Context context) {
        dbHelper = new DBHelper(context, DBHelper.DB_NAME, null, DBHelper.DB_VERSION);
    }

    public void open() throws SQLException
    {
        database = dbHelper.getWritableDatabase();
    }
    public void close() {
        dbHelper.close();
    }
    public void createUser(DataUser dataUser){
        open();
        ContentValues values = new ContentValues();
        values.put(DBHelper.USER_NAME, dataUser.getU_NAME());
        values.put(DBHelper.USER_PASSWORD, dataUser.getU_PASSWORD());
        database.insert(DBHelper.TABLE_USER, null, values);
        database.close();
    }
    public boolean checkName(String name){
        database = dbHelper.getReadableDatabase();
        String[] columns = {
                DBHelper.USER_ID
        };
        String selection = DBHelper.USER_NAME + " = ?";
        String[] selectionArgs = {name};
        Cursor cursor = database.query(DBHelper.TABLE_USER,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();
        cursor.close();
        close();

        if (cursorCount > 0) {
            return true;
        }

        return false;

    }
    public boolean checkUser(String email, String password) {

        // array of columns to fetch
        String[] columns = {
                DBHelper.USER_ID
        };
        database = dbHelper.getReadableDatabase();
        // selection criteria
        String selection = DBHelper.USER_NAME + " = ?" + " AND " +  DBHelper.USER_PASSWORD + " = ?";

        String[] selectionArgs = {email, password};


        Cursor cursor = database.query(DBHelper.TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        database.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }
    public List<DataUser> getAllUser() {
        // array of columns to fetch
        String[] columns = {
                DBHelper.USER_ID,
                DBHelper.USER_NAME,
                DBHelper.USER_PASSWORD
        };
        // sorting orders
        String sortOrder =
                DBHelper.USER_NAME + " ASC";
        List<DataUser> userList = new ArrayList<DataUser>();

        database = dbHelper.getReadableDatabase();

        Cursor cursor = database.query(DBHelper.TABLE_USER, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DataUser user = new DataUser();
                user.setU_ID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(DBHelper.USER_ID))));
                user.setU_NAME(cursor.getString(cursor.getColumnIndex(DBHelper.USER_NAME)));
                user.setU_PASSWORD(cursor.getString(cursor.getColumnIndex(DBHelper.USER_PASSWORD)));
                // Adding user record to list
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();

        // return user list
        return userList;
    }
    public long createRoti(DataRoti dataRoti) {
        open();
        ContentValues values = new ContentValues();
        values.put(DBHelper.R_KODE,dataRoti.getR_KODE());
        values.put(DBHelper.R_NAMA ,dataRoti.getR_NAMA());
        values.put(DBHelper.R_DESKRIPSI ,dataRoti.getR_DESKRIPSI());
        values.put(DBHelper.R_HARGA, dataRoti.getR_HARGA());
        values.put(DBHelper.R_IMAGE, dataRoti.getR_IMAGE());
        values.put(DBHelper.R_SELECTION ,dataRoti.getR_SELECTION());

        long insertId = database.insert(DBHelper.TABLE_ROTI, null,values);
        close();
        return insertId;
    }
    public long createBeli(DataBeli dataBeli) {
        open();
        ContentValues values = new ContentValues();
        values.put(DBHelper.B_KODE,dataBeli.getB_KODE());
        values.put(DBHelper.B_NAMA ,dataBeli.getB_NAMA());
        values.put(DBHelper.B_HARGA ,dataBeli.getB_HARGA());
        values.put(DBHelper.B_JUMLAH, dataBeli.getB_JUMLAH());
        values.put(DBHelper.B_TOTAL ,dataBeli.getB_TOTAL());
        values.put(DBHelper.B_IMAGE ,dataBeli.getB_IMAGE());
        values.put(DBHelper.B_SELECTION, dataBeli.getB_SELECTION());


        long insertId = database.insert(DBHelper.TABLE_BELI, null,values);
        close();
        return insertId;
    }


    private DataRoti cursorToForm(Cursor cursor) {
        open();
        DataRoti data = new DataRoti();
        data.setR_KODE(cursor.getString(0));
        data.setR_NAMA(cursor.getString(1));
        data.setR_DESKRIPSI(cursor.getString(2));
        data.setR_HARGA(cursor.getString(3));
        data.setR_IMAGE(cursor.getString(4));
        data.setR_SELECTION(cursor.getString(5));

        close();
        return data;
    }
    private DataBeli cursorToTransaksi(Cursor cursor) {
        open();
        DataBeli data = new DataBeli();
        data.setB_KODE(cursor.getString(0));
        data.setB_NAMA(cursor.getString(1));
        data.setB_HARGA(cursor.getString(2));
        data.setB_JUMLAH(cursor.getString(3));
        data.setB_TOTAL(cursor.getString(4));
        data.setB_IMAGE(cursor.getString(5));
        data.setB_SELECTION(cursor.getString(6));
        close();
        return data;
    }
    public ArrayList<DataRoti> getRotiBasah() {
        open();
        ArrayList<DataRoti> listdata = new ArrayList();
        Cursor cursor = this.database.query(DBHelper.TABLE_ROTI, this.rotidata, DBHelper.R_SELECTION+"=0", null, null, null, DBHelper.R_KODE + " ASC");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            listdata.add(cursorToForm(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        close();
        return listdata;
    }
    public ArrayList<DataRoti> getRotiKering() {
        open();
        ArrayList<DataRoti> listdata = new ArrayList();
        Cursor cursor = this.database.query(DBHelper.TABLE_ROTI, this.rotidata, DBHelper.R_SELECTION+"=1", null, null, null, DBHelper.R_KODE + " ASC");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            listdata.add(cursorToForm(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        close();
        return listdata;
    }
    public ArrayList<DataRoti> getAllRoti() {
        open();
        ArrayList<DataRoti> listdata = new ArrayList();
        Cursor cursor = this.database.query(DBHelper.TABLE_ROTI, this.rotidata, DBHelper.R_SELECTION+"=0", null, null, null, DBHelper.R_KODE + " ASC");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            listdata.add(cursorToForm(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        close();
        return listdata;
    }
    public ArrayList<DataRoti> getRotibyKode(String kode) {
        open();
        ArrayList<DataRoti> listdata = new ArrayList();
        Cursor cursor = this.database.query(DBHelper.TABLE_ROTI, this.rotidata, DBHelper.R_KODE+"='"+kode+"'", null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            listdata.add(cursorToForm(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        close();
        return listdata;
    }
    public ArrayList<DataBeli> getPembelian() {
        open();
        ArrayList<DataBeli> listdata = new ArrayList();
        Cursor cursor = this.database.query(DBHelper.TABLE_BELI, this.belidata, DBHelper.B_SELECTION+"=0", null, null, null, DBHelper.B_KODE + " ASC");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            listdata.add(cursorToTransaksi(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        close();
        return listdata;
    }
    public ArrayList<DataBeli> getPembelianbyKode(String kode) {
        open();
        ArrayList<DataBeli> listdata = new ArrayList();
        Cursor cursor = this.database.query(DBHelper.TABLE_BELI, this.belidata, DBHelper.B_KODE+"='"+kode+"'", null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            listdata.add(cursorToTransaksi(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        close();
        return listdata;
    }
    public long deleteRoti(String kode) {
        open();
        long a = this.database.delete(DBHelper.TABLE_ROTI, DBHelper.R_KODE+"='"+kode+"'", null);
        close();
        return a;
    }
    public long deleteBeli(String kode) {
        open();
        long a = this.database.delete(DBHelper.TABLE_BELI, DBHelper.B_KODE+"='"+kode+"'", null);
        close();
        return a;
    }
    public long updateRoti(DataRoti dataRoti) {
        open();
        ContentValues values = new ContentValues();
        values.put(DBHelper.R_KODE,dataRoti.getR_KODE());
        values.put(DBHelper.R_NAMA ,dataRoti.getR_NAMA());
        values.put(DBHelper.R_DESKRIPSI ,dataRoti.getR_DESKRIPSI());
        values.put(DBHelper.R_HARGA, dataRoti.getR_HARGA());
        values.put(DBHelper.R_IMAGE, dataRoti.getR_IMAGE());
        values.put(DBHelper.R_SELECTION ,dataRoti.getR_SELECTION());


        long updateId = database.update(DBHelper.TABLE_ROTI, values, DBHelper.R_KODE+"='"+dataRoti.getR_KODE()+"'", null);
        close();
        return updateId;
    }
    public long updateTransaksi(DataBeli dataBeli) {
        open();
        ContentValues values = new ContentValues();
        values.put(DBHelper.B_KODE,dataBeli.getB_KODE());
        values.put(DBHelper.B_NAMA ,dataBeli.getB_NAMA());
        values.put(DBHelper.B_HARGA ,dataBeli.getB_HARGA());
        values.put(DBHelper.B_JUMLAH, dataBeli.getB_JUMLAH());
        values.put(DBHelper.B_TOTAL ,dataBeli.getB_TOTAL());
        values.put(DBHelper.B_IMAGE ,dataBeli.getB_IMAGE());
        values.put(DBHelper.B_SELECTION, dataBeli.getB_SELECTION());


        long updateId = database.update(DBHelper.TABLE_BELI, values, DBHelper.B_KODE+"='"+dataBeli.getB_KODE()+"'", null);
        close();
        return updateId;
    }

}
