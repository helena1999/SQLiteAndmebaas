package com.example.opilane.sqliteandmebaas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLData;

/**
 * Created by opilane on 21.02.2018.
 */

public class DbToode {
    Context sisu;
    SQLiteDatabase db;
    DbToode.DbHelper dbHelper;

    public DbToode(Context s){
        sisu = s;
    }
    public DbToode ava(){
        try {
            dbHelper = new DbHelper(sisu);
            db = dbHelper.getWritableDatabase();
        }
        catch (Exception e){
            e.printStackTrace();
        }finally {
            return this;
        }
    }
    public void sulge (){db.close();}
    public void insert (String nimi, int kogus, double hind){
        ContentValues values = new ContentValues();
        values.put(dbHelper.NIMI, nimi);
        values.put(dbHelper.KOGUS, kogus);
        values.put(dbHelper.HIND, hind);
        db.insert(dbHelper.TABELI_NIMI, null, values);
    }
    public Cursor kuvaAndmed (){
        String[] tulbad = new String [] {dbHelper.ID, dbHelper.NIMI, dbHelper.KOGUS, dbHelper.HIND};
        Cursor s = db.query(dbHelper.TABELI_NIMI, tulbad, null, null, null, null, dbHelper.ID + "desc");
        if (s != null){
            s.moveToFirst();
        }
        return s;
    }
    public Cursor valitud(long id){
        String[] tulbad = new String [] {dbHelper.ID, dbHelper.NIMI, dbHelper.KOGUS, dbHelper.HIND};
        Cursor s = db.query(dbHelper.TABELI_NIMI, tulbad, dbHelper.ID + "=" + id, null, null, null, null);
        if (s != null){
            s.moveToFirst();
        }
        return s;

    }

    public class DbHelper extends SQLiteOpenHelper{
        public static final String DB_NIMI = "toode.db";
        public static final String TABELI_NIMI = "tooted";
        public static final String ID = "_id";
        public static final String NIMI = "nimi";
        public static final String KOGUS = "kogus";
        public static final String HIND = "hind";

        public static final String CREATE_TABLE = "CREATE TABLE" + TABELI_NIMI + "(" + ID + "INTEGER PRIMARY KET AUTOINCREMENT, " + NIMI + "TEXT, " + KOGUS + "INTEGER, " + HIND + "DOUBLE);";
        public static final int VERSION = 1;

        public DbHelper (Context context){
            super(context, DB_NIMI, null, VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABELI_NIMI);

            onCreate(db);

        }
    }
}
