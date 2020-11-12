package com.example.wifestonic.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.wifestonic.Model.RecordModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static int VERSION = 1;
    private static String NAME = "recordListDatabase";
    private static String RECORD_TABLE = "record";
    private static String ID = "id";
    private static String STATUS = "status";
    private static String RECORD_TEXT = "recordText";
    private static String CREATE_RECORD_TABLE = "CREATE TABLE " + RECORD_TABLE + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                                                + RECORD_TEXT + " TEXT, " + STATUS + " INTEGER)";

    private SQLiteDatabase dataBase;
    public DatabaseHandler(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_RECORD_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + RECORD_TABLE);
    }

    public void accessDatabase() {
        dataBase = this.getWritableDatabase();
    }

    public void addRecord(RecordModel model) {
        ContentValues content = new ContentValues();
        content.put(RECORD_TEXT, model.getRecordText());
        content.put(STATUS, model.isChecked() ? 1 : 0);
        dataBase.insert(RECORD_TABLE, null, content);
    }
//Nic sie nie zmieni dzieki temu
    public List<RecordModel> getAllRecords() {
        List<RecordModel> list = new ArrayList<>();
        Cursor cursor = null;
        dataBase.beginTransaction();
        try{
            cursor = dataBase.query(RECORD_TABLE, null, null,
                    null, null, null, null, null);
            if(cursor != null) {
                if(cursor.moveToFirst()) {
                    do {
                            RecordModel model = new RecordModel();
                            model.setId(cursor.getInt(cursor.getColumnIndex(ID)));
                            model.setRecordText(cursor.getString(cursor.getColumnIndex(RECORD_TEXT)));
                            model.setChecked(cursor.getInt(cursor.getColumnIndex(STATUS)) == 1 ? true : false);
                            list.add(model);
                    } while(cursor.moveToNext());
                }
            }
        }
        finally {
            dataBase.endTransaction();
            cursor.close();
        }

        return list;
    }

    public void updateRecordStatus(int id, boolean status) {
        ContentValues content = new ContentValues();
        int statusNum = status ? 1 : 0;
        content.put(STATUS, statusNum);
        dataBase.update(RECORD_TABLE, content, ID + "=?", new String[] {String.valueOf(id)});
    }

    public void updateRecordText(int id, String text) {
        ContentValues content = new ContentValues();
        content.put(RECORD_TEXT, text);
        dataBase.update(RECORD_TABLE, content, ID + "=?", new String[] {String.valueOf(id)});
    }

    public void deleteRecord(int id) {
        dataBase.delete(RECORD_TABLE, ID + "=?", new String[] {String.valueOf(id)});
    }
}
