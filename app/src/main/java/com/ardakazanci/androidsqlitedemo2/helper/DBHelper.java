package com.ardakazanci.androidsqlitedemo2.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import com.ardakazanci.androidsqlitedemo2.model.Account;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {


    private static String DB_PATH = "";
    private static String DB_NAME = "SampleDB.db";

    private SQLiteDatabase mDatabase;

    private Context context = null;


    public DBHelper(Context context) {
        super(context, DB_NAME, null, 1);

        DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";

        this.context = context;


    }

    // İlgili veritabanı var mı yok mu kontrolünün yapılması.
    private boolean checkDatabase() {
        SQLiteDatabase tempDB = null;

        try {


            String path = DB_PATH + DB_NAME;
            tempDB = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);

        } catch (Exception e) {

            e.printStackTrace();

        }

        if (tempDB != null) {
            tempDB.close();
        }

        return tempDB != null ? true : false;

    }

    // İlgili veritabanı var ise içeriğinin okunması ve yeni bir file ' a yazılması.
    public void copyDatabase() {

        try {
            InputStream myStream = context.getAssets().open(DB_NAME);
            String outputFileName = DB_PATH + DB_NAME;
            OutputStream myOutput = new FileOutputStream(outputFileName);

            byte[] buffer = new byte[1024];
            int length;

            while ((length = myStream.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }

            myOutput.flush();
            myOutput.close();
            myStream.close();


        } catch (IOException e) {
            Log.e("Copy",e.toString());
            e.printStackTrace();
        }

    }

    public void openDatabase() {
        String path = DB_PATH + DB_NAME;
        mDatabase = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public void createDatabase() {
        boolean isDBExists = checkDatabase();

        if (isDBExists) {



        } else {
            this.getReadableDatabase();
            try {

                copyDatabase();

            } catch (Exception e) {

                e.printStackTrace();

            }
        }

    }

    public List<Account> getAllData() {

        List<Account> temp = new ArrayList<Account>();

        SQLiteDatabase database = this.getWritableDatabase();

        Cursor c;

        try {

            c = database.rawQuery("SELECT * FROM Account_1", null);
            if (c == null) {
                Log.e("C","Null");
                return null;

            }

            c.moveToFirst();

            do {

                Account account = new Account(c.getString(c.getColumnIndex("UserName")),

                        c.getString(c.getColumnIndex("Email"))
                );
                temp.add(account);


            } while (c.moveToNext());

            c.close();


        } catch (Exception e) {
            e.printStackTrace();
        }

        database.close();
        return temp;

    }


    @Override
    public synchronized void close() {
        if (mDatabase != null) {
            mDatabase.close();
        }
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
