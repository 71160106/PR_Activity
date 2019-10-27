package com.example.latihanlayout;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DataHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "data.db";
    public static final String CONTACTS_TABLE_NAME = "mahasiswa";
    public static final String CONTACTS_COLUMN_NIM= "nim";
    public static final String CONTACTS_COLUMN_NAMA = "nama";

    public DataHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL = "CREATE TABLE "+ CONTACTS_TABLE_NAME+"("+
                CONTACTS_COLUMN_NIM+" TEXT NOT NULL PRIMARY KEY, "+CONTACTS_COLUMN_NAMA+" TEXT NOT NULL)";
        sqLiteDatabase.execSQL(SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS mahasiswa");
        onCreate(sqLiteDatabase);
    }

    public boolean insertContact (String nim,String nama) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nim", nim);
        contentValues.put("name", nama);
        sqLiteDatabase.insert("mahasiswa", null, contentValues);
        return true;
    }

    public Cursor getData(int nim) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor res =  sqLiteDatabase.rawQuery( "select * from mahasiswa where nim="+nim+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(sqLiteDatabase, CONTACTS_TABLE_NAME);
        return numRows;
    }

    public ArrayList<String> getAllCotacts() {
        ArrayList<String> array_list = new ArrayList<String>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor res =  sqLiteDatabase.rawQuery( "select * from mahasiswa", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAMA)));
            res.moveToNext();
        }
        return array_list;
    }
}
