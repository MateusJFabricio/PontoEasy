package com.example.pontoeasy;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BancoDados extends SQLiteOpenHelper {

    public String nomeBancoDados = "Easy.db";
    public String tblUsuarios = "USUARIO";

    public BancoDados(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table " + nomeBancoDados + "(ID INTEGER PRIMARY " +
        " KEY AUTOINCREMENT, NOME TEXT, SOBRENOME TEXT, NOTA INTEGER)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + nomeBancoDados;
        db.execSQL(query);
        onCreate(db);
    }

    public boolean insertDB(String nome, String email, String senha)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("NOME", nome);
        cv.put("EMAIL", email);
        cv.put("SENHA", senha);

        long result = db.insert(tblUsuarios,null, cv);

        return result > 0;


    }
}
