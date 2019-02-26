package com.example.xisco.missatgeria;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DataSourcePrefs {
    private SQLiteDatabase database;
    private HelperQuepassaeh dbAjuda;
    private String[] allColumns = {HelperQuepassaeh.COLUMN_CODI, HelperQuepassaeh.COLUMN_CODIUSUARI, HelperQuepassaeh.COLUMN_DATAHORA,
            HelperQuepassaeh.COLUMN_EMAIL, HelperQuepassaeh.COLUMN_FKCODIUSUARI, HelperQuepassaeh.COLUMN_FOTO, HelperQuepassaeh.COLUMN_MSG,
            HelperQuepassaeh.COLUMN_NOM, HelperQuepassaeh.COLUMN_PENDENT, HelperQuepassaeh.COLUMN_PASSWORD};
    public DataSourcePrefs(Context context) { //CONSTRUCTOR
        dbAjuda = new HelperQuepassaeh(context);
    }

    public void open() throws SQLException {
        database = dbAjuda.getWritableDatabase();
    }

    public void close() {
        dbAjuda.close();
    }

    public void guardarPrefs(Preferencies prefs) {
        // insert d'un nou llistavins
        ContentValues values = new ContentValues();
        values.put(HelperQuepassaeh.COLUMN_CODIUSUARI, prefs.getCodiusuari());
        values.put(HelperQuepassaeh.COLUMN_NOM, prefs.getCodiusuari());
        values.put(HelperQuepassaeh.COLUMN_PASSWORD, prefs.getCodiusuari());
        values.put(HelperQuepassaeh.COLUMN_CODIUSUARI, prefs.getCodiusuari());
    }
}
