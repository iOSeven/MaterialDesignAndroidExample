package com.hallmg.alferza.materialdesign;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONArray;

/**
 * Created by administrador on 22/06/15.
 */
public class DataBaseManager {
    //Instancia de las clases
    private DBHelper helper;
    private SQLiteDatabase db;

    //Metodo de Inicializacion
    public DataBaseManager(Context context) {
        helper = new DBHelper(context);

        db = helper.getWritableDatabase();
    }


    /*Esta clase es la encargada de hacer movimientos en la DB*/

    /*################### ESTRUCTURA DE LA DB #############################################*/
    //Nombre de la tabla server
    public static final String TABLE_SERVER = "server";
    //Nombre de la tabla usuarios
    public static final String TABLE_USERS = "usuarios";
    //Nombre de la tabla de login
    public static final String TABLE_LOGIN = "login";



    //Nombre de campos server
    public static final String CN_ID = "_id";
    public static final String CN_SERVER_DIRECTION = "server_direction";
    public static final String CN_STATUS = "status";
    //Nombre de campos usuarios
    public static final String CN_USER = "usuario";
    public static final String CN_PASSWORD = "password";
    public static final String CN_TYPE = "tipo";
    //Nombre de campos de la tabla de login
    public static final String CN_ID_USER = "id_user";


    //Sentencia para crear la tabla server
    public static final String CREATE_TABLE_SERVER = "create table "+TABLE_SERVER+" ("
            + CN_ID + " integer primary key autoincrement,"
            + CN_SERVER_DIRECTION + " text not null,"
            + CN_STATUS + " int);";

    //Sentencia para crear la tabla usuarios
    public static final String CREATE_TABLE_USERS = "create table "+TABLE_USERS+" ("
            + CN_ID + " integer primary key autoincrement,"
            + CN_USER + " text not null,"
            + CN_PASSWORD + " text not null,"
            + CN_TYPE + " text not null);";

    //Sentencia para crear la tabla de login
    public static final String CREATE_TABLE_LOGIN = "create table "+TABLE_LOGIN+" ("
            + CN_ID + " integer primary key autoincrement,"
            + CN_ID_USER + " integer);";


    /*################### INSERTAR #############################################*/

    //Metodo insertar parametros del servidor PHP
    public void InsertarParams(String server_direction) {
        //Modificar el estatus de las entradas anteriores
        db.update(TABLE_SERVER, generarContentValuesStatus(), null, null);
        //Insertar el registro en la DB SQLite
        db.insert(TABLE_SERVER, null, generarContentValues(server_direction));
    }


    //Metodo para insertar usuarios
    public void InsertParamsUsers(int id, String user, String password, String type) {
        //Instruccion para insertar en android
        db.insert(TABLE_USERS, null, generarContentValuesUsers(id, user, password, type));
    }

    //Metodo para insertar en login
    public void InsertParamsLogin(int id_user) {
        //Instruccion para insertar en android
        db.insert(TABLE_LOGIN, null, generarContentValuesLogin(id_user));
    }

    /*################### FIN INSERTAR #############################################*/

    /*################### BORRAR DATOS LOGIN #############################################*/

    public void clearDataLogin(){
        db.delete(TABLE_LOGIN, null, null);
    }

    /*################### FIN BORRAR DATOS LOGIN #############################################*/


    /*################### RECUPERAR DATOS #############################################*/

    //Recuperamos los datos del servidor
    public Cursor findServerData(String status) {
        //Se crea el array de las columnas que seran consultadas
        String[] columnas = new String[]{CN_SERVER_DIRECTION};

        //Recupera la informacion del estatus que queremos
        return db.query(TABLE_SERVER, columnas, CN_STATUS + "=?", new String[]{status}, null, null, null);
    }

    //Comprobar so existen login
    public Cursor findDataLogin() {
        //Se crea el array de columnas de la tabla
        String[] columnas = new String[] {CN_ID, CN_ID_USER};
        //Se recupera informacion de la tabla
        return db.query(TABLE_LOGIN,columnas,null, null, null, null, null);
    }

    /*################### RECUPERAR DATOS #############################################*/

    /*#########################    CONTENEDORES   ###############################################*/

    //Metodo para contenedor de valores "Reutilizacion de codigo"
    private ContentValues generarContentValuesStatus() {
        ContentValues values = new ContentValues();
        values.put(CN_STATUS, 0);

        return values;
    }

    //Metodo para contenedor de valores "Reutilizacion de codigo"
    private ContentValues generarContentValues(String server_direction) {
        ContentValues values = new ContentValues();
        values.put(CN_SERVER_DIRECTION, server_direction);
        values.put(CN_STATUS, 1);

        return values;
    }

    //Metodo contenedor de valores USUARIOS
    private ContentValues generarContentValuesUsers(int id,String user, String password, String type) {
        ContentValues values = new ContentValues();
        values.put(CN_ID, id);
        values.put(CN_USER, user);
        values.put(CN_PASSWORD, password);
        values.put(CN_TYPE, type);

        return values;
    }

    //Metodo contenedor de valores LOGIN
    private  ContentValues generarContentValuesLogin(int id_user) {
        ContentValues values = new ContentValues();
        values.put(CN_ID_USER, id_user);
        return values;
    }

    /*#########################    CONTENEDORES   ###############################################*/


    /*########################### SERVER OPERATIONS #############################################*/

    //Metodo para leer respuesta del login en el server
    public int obtDatosJSONLogin(String response) {
        int acceso = 0;
        try {
            //Limpiamos el registro de login
            clearDataLogin();
            //resibimos el arreglo de tipo JSON en una variable JSON
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0;i<=jsonArray.length();i++) {
                InsertParamsLogin(Integer.parseInt(jsonArray.getJSONObject(i).getString("id")));
                acceso = Integer.parseInt(jsonArray.getJSONObject(i).getString("id"));
            }
        } catch (Exception e) {
            return acceso;
        }
        return acceso;
    }

    //Metodo para leer respuesta del login en el server
    public String obtDatosJSONSync1(String response) {
        String msg = "Ocurrio un error intente mas tarde.";
        try {
            //resibimos el arreglo de tipo JSON en una variable JSON
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0;i<=jsonArray.length();i++) {
                msg = jsonArray.getJSONObject(i).getString("msg");
            }
        } catch (Exception e) {
            return msg;
        }
        return msg;
    }

    /*########################### SERVER OPERATIONS #############################################*/
}