package com.example.aditya.workout;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

/**
 * Created by aditya on 20/3/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper{
    String DB_PATH = null;
    private static String DB_NAME = "AHIIT.sqlite";
    public SQLiteDatabase ahiitDatabase;
    private final Context ahiitContext;

    public DatabaseHelper(Context context){

        super(context,DB_NAME,null,10);
        this.ahiitContext = context;
        this.DB_PATH = "/data/data/" + context.getPackageName() + "/" + "databases/";
    }

    public void createDatabase() throws IOException {
        boolean dbExist = checkDatabase();
        if(dbExist){

        }
        else{
                this.getReadableDatabase();
                try{
                    copyDatabase();
                }catch(IOException e){
                    throw new Error("Error in copying Database!");
                }

        }
    }

    private boolean checkDatabase(){
        SQLiteDatabase checkDB = null;
        try{
            String ahiitPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(ahiitPath,null,SQLiteDatabase.OPEN_READWRITE);
        }catch(SQLiteException sqle) {
        }
        if(checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false ;
    }

    private void copyDatabase() throws IOException{
        InputStream ahiitInput = ahiitContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream ahiitOutput =  new FileOutputStream(outFileName);
        byte[] buffer = new byte[10];
        int length;
        while((length = ahiitInput.read(buffer)) > 0){
            ahiitOutput.write(buffer,0,length);
        }
        ahiitOutput.flush();
        ahiitOutput.close();
        ahiitInput.close();
    }

    public void openDatabase() throws SQLException{
        String ahiitPath = DB_PATH + DB_NAME;
        ahiitDatabase = SQLiteDatabase.openDatabase(ahiitPath,null,SQLiteDatabase.OPEN_READWRITE);
    }

    public synchronized void close(){
        if(ahiitDatabase != null) {
            ahiitDatabase.close();
        }
        super.close();
    }

    public void onCreate(SQLiteDatabase db){

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        if(newVersion > oldVersion){
            try{
                copyDatabase();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy){
        return ahiitDatabase.query("Exercise",null,null,null,null,null,null);
    }


}
