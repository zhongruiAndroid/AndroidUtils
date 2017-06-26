package com.github.test;

import android.content.ContentValues;
import android.content.Context;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

/**
 * Created by Administrator on 2017/6/17.
 */
public class DBManager extends SQLiteOpenHelper{
    SQLiteDatabase db = this.getWritableDatabase("shanghai");
    private int id;

    private DBManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public static DBManager newInstance(Context context) {
        return new DBManager(context,"test",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table Test( _id integer,name text)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addData(int id,String name){
        ContentValues values=new ContentValues();
        values.put("_id",id);
        values.put("name",name);
        db.insert("Test",null,values);
    }
    public String  selectData(int id){
        String _id=null;
        String name=null;
        Cursor cursor = db.query("Test", new String[]{"_id", "name"},"_id=?",new String[]{id+""}, null, null, null);
        if(cursor!=null){
            while (cursor.moveToNext()){
                _id = cursor.getString(cursor.getColumnIndex("_id"));
                name = cursor.getString(cursor.getColumnIndex("name"));
            }
        }
        cursor.close();
        return _id+name;
    }
}
