package com.hallmg.alferza.materialdesign;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by administrador on 22/06/15.
 */
public class DBHelper extends SQLiteOpenHelper {

    /*Crear el nombre de la DB en SQLite, puede o no llevar la extension eso no afecta*/
    private static final String DB_NAME = "notificaciones.sqlite";

    /*La version de la DB es importante por que al tener una actualizacion se deba cambiar la version
    * y se ejecuta un Metodo de upgrade*/
    private  static final int DB_VERSION = 1;

    /*Constructor de la clase es muy importante*/
    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //En esta parte se va a crear toda la estructura de la DB
        db.execSQL(DataBaseManager.CREATE_TABLE_SERVER);
        db.execSQL(DataBaseManager.CREATE_TABLE_USERS);
        db.execSQL(DataBaseManager.CREATE_TABLE_LOGIN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) {

    }
}
