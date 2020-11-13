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

    private SQLiteDatabase dataBase;
    public DatabaseHandler(Context context) {
        super(context, DataBaseNames.NAME, null, DataBaseNames.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DataBaseNames.CREATE_RECORD_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DataBaseNames.RECORD_TABLE);
    }

    //Otwieramy baze danych do modyfikacji
    public void accessDatabase() {
        dataBase = this.getWritableDatabase();
    }

    //Dodajemy nowy rekord
    public void addRecord(RecordModel model) {
        ContentValues content = new ContentValues();
        content.put(DataBaseNames.RECORD_TEXT, model.getRecordText());
        content.put(DataBaseNames.STATUS, model.isChecked() ? 1 : 0);
        dataBase.insert(DataBaseNames.RECORD_TABLE, null, content);
    }

    //Odpytujemy baze danych o wszystkie istniejace rekordy
    public List<RecordModel> getAllRecords() {
        List<RecordModel> list = new ArrayList<>();
        Cursor cursor = null;

        //Rozpoczynamy transakcje, aby nie bylo mozliwosci wykonania innych akcji na bazie danych
        dataBase.beginTransaction();

        try{
            //dostajemy "wskaznik" na wszystko co zwroci nam zapytanie o tabele
            cursor = dataBase.query(DataBaseNames.RECORD_TABLE, null, null,
                    null, null, null, null, null);
            //Jezeli cokolwiek zostalo zwrocone
            if(cursor != null) {
                //Probujemy odczytac pierwszy element
                if(cursor.moveToFirst()) {
                    //Przechodzimy przez wszystkie dane i dodajemy je do modelu, ktory dodajemy do listy modeli
                    do {
                            RecordModel model = new RecordModel();
                            model.setId(cursor.getInt(cursor.getColumnIndex(DataBaseNames.ID)));
                            model.setRecordText(cursor.getString(cursor.getColumnIndex(DataBaseNames.RECORD_TEXT)));
                            model.setChecked(cursor.getInt(cursor.getColumnIndex(DataBaseNames.STATUS)) == 1 ? true : false);
                            list.add(model);
                    } while(cursor.moveToNext());
                }
            }
        }
        finally {
            //konczymy transakcje aby zwolic baze danych
            dataBase.endTransaction();
            cursor.close();
        }

        return list;
    }

    //Aktualizacja samego statusu
    public void updateRecordStatus(int id, boolean status) {
        ContentValues content = new ContentValues();
        int statusNum = status ? 1 : 0; // w bazie danych mamy inty, w RecordModel mamy boolean
        content.put(DataBaseNames.STATUS, statusNum);
        dataBase.update(DataBaseNames.RECORD_TABLE, content, DataBaseNames.ID + "=?", new String[]{String.valueOf(id)});
    }

    //Aktualizacja samego tekstu
    public void updateRecordText(int id, String text) {
        ContentValues content = new ContentValues();
        content.put(DataBaseNames.RECORD_TEXT, text);
        dataBase.update(DataBaseNames.RECORD_TABLE, content, DataBaseNames.ID + "=?", new String[] {String.valueOf(id)});
    }

    // Usuniecie wpisu w bazie danych
    public void deleteRecord(int id) {
        dataBase.delete(DataBaseNames.RECORD_TABLE, DataBaseNames.ID + "=?", new String[] {String.valueOf(id)});
    }
}
