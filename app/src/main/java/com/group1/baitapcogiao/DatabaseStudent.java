package com.group1.baitapcogiao;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.os.ParcelUuid;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;

public class DatabaseStudent extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Sinhvien.db";
    public static final String TABLE_NAME = "Sinhvien";

    public DatabaseStudent(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public boolean insertStudent(SinhVien sinhVien){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        try{
            String query = "INSERT INTO "+TABLE_NAME+" VALUES ('"+sinhVien.id+"','"+sinhVien.name+"','"+sinhVien.birthday+"', "+sinhVien.sex+" ,'"+sinhVien.address+"' )";
            sqLiteDatabase.execSQL(query);
            return true;
        }catch (Exception ex){
            return false;
        }
    }
    public ArrayList<SinhVien> getListSinhVien(){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        ArrayList<SinhVien> list = new ArrayList<>();
        try {
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+ TABLE_NAME,null);
            if(cursor.getCount()>=0){
                while (cursor.moveToNext()){
                    SinhVien sinhVien = new SinhVien(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getInt(3),cursor.getString(4));
                    list.add(sinhVien);
                }
            }else {
                Log.d("EX", "Empty");
            }

        }catch (Exception ex){
            Log.d("GGGGG", "getListSinhVien: "+ex.getMessage());
        }
        return list;
    }
    public boolean editStudent(SinhVien sinhVien){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        try {
            sqLiteDatabase.execSQL("UPDATE "+ TABLE_NAME+" SET name = '"+sinhVien.name+"' , birthday = '"+sinhVien.birthday+"', sex = "+sinhVien.sex+" , address = '"+sinhVien.address+"' WHERE ID = '"+sinhVien.id+"'");
            return true;
        }catch (Exception ex){
            return false;
        }
    }
    public boolean deleteStudent(String id){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        try {
            sqLiteDatabase.execSQL("DELETE FROM "+ TABLE_NAME + " WHERE ID = '"+id+"'");
            return true;
        }catch (Exception ex){
            return false;
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_NAME +"(ID CHAR(20) PRIMARY KEY, name NVARCHAR(50) , birthday CHAR(30), sex INT , address NVARCHAR(200)) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
