package com.example.xisco.missatgeria;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

public class DataSourcePrefs {
    private SQLiteDatabase database;
    private HelperQuepassaeh dbAjuda;
    private String[] allColumns = {HelperQuepassaeh.COLUMN_CODI, HelperQuepassaeh.COLUMN_CODIUSUARI, HelperQuepassaeh.COLUMN_DATAHORA,
            HelperQuepassaeh.COLUMN_EMAIL, HelperQuepassaeh.COLUMN_FKCODIUSUARI, HelperQuepassaeh.COLUMN_FOTO, HelperQuepassaeh.COLUMN_MSG,
            HelperQuepassaeh.COLUMN_NOM, HelperQuepassaeh.COLUMN_PENDENT};
    public DataSourcePrefs(Context context) { //CONSTRUCTOR
        dbAjuda = new HelperQuepassaeh(context);
    }

    public void open() throws SQLException {
        database = dbAjuda.getWritableDatabase();
    }

    public void close() {
        dbAjuda.close();
    }

    public void guardarUsuari(String codi, String nom, String email) {
        ContentValues values = new ContentValues();
        values.put(HelperQuepassaeh.COLUMN_CODIUSUARI, codi);
        values.put(HelperQuepassaeh.COLUMN_NOM, nom);
        values.put(HelperQuepassaeh.COLUMN_EMAIL, email);
        database.insert(HelperQuepassaeh.TABLE_USUARI, null, values);
    }
    public void guardaMissatges(String codi, String datahora, String msg, String codiUsuari){
        ContentValues values = new ContentValues();
        values.put(HelperQuepassaeh.COLUMN_CODI, codi);
        values.put(HelperQuepassaeh.COLUMN_DATAHORA, datahora);
        values.put(HelperQuepassaeh.COLUMN_MSG, msg);
        values.put(HelperQuepassaeh.COLUMN_FKCODIUSUARI, codiUsuari);
        database.insert(HelperQuepassaeh.TABLE_MISSATGE, null, values);
    }
    public void getAllMissatges(){
        String Query = "Select * from usuari";
        Cursor cursor = database.rawQuery(Query, null);
        String c = String.valueOf(cursor.getCount());
        Log.i("count", c);
    }
    public boolean usuariExist(String fieldValue) {
        String Query = "Select * from " + HelperQuepassaeh.TABLE_USUARI + " where " + HelperQuepassaeh.COLUMN_CODIUSUARI + " = " + fieldValue;
        Cursor cursor = database.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }
}
