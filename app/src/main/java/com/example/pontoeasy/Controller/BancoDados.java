package com.example.pontoeasy.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.security.keystore.UserPresenceUnavailableException;

import androidx.annotation.Nullable;

import com.example.pontoeasy.DAO.Ponto;
import com.example.pontoeasy.DAO.Usuario;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PipedOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BancoDados extends SQLiteOpenHelper {

    private String nomeBancoDados = "Easy.db";
    private String tblUsuarios = "USUARIO";
    private String tblPonto = "PONTO";

    public BancoDados(@Nullable Context context,
                      @Nullable String name,
                      @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "EasyPonto2.db", factory, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Cria a tabela USUARIOS
        String query = "create table " + tblUsuarios + "(" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "NOME TEXT, " +
                "EMAIL TEXT, " +
                "SENHA TEXT," +
                "FOTO BLOB" +
                ")";
        db.execSQL(query);

        //Cria a tabela PONTO
        query = "create table " + tblPonto + "(" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "DATAHORA TEXT, " +
                "LONGITUDE TEXT, " +
                "LATITUDE TEXT, " +
                "TIPO INTEGER," +
                "JUSTIFICATIVA TEXT," +
                "STATUS INTEGER," +
                "USUARIOID INTEGER" +
                ")";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + nomeBancoDados;
        db.execSQL(query);
        onCreate(db);
    }

    public Usuario insertUsuario(String nome, String email, String senha, Bitmap foto)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("NOME", nome);
        cv.put("EMAIL", email);
        cv.put("SENHA", senha);

        //Inserir foto
        if (foto != null)
        {
            //Converte a foto para bitmap
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            foto.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            foto.recycle();

            cv.put("FOTO", byteArray);
        }

        long result = db.insert(tblUsuarios,null, cv);
        return buscarUsuario(email, senha);
    }

    public Usuario buscarUsuario(String email, String senha)
    {
        Usuario usuario = new Usuario();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT " +
                "ID, " +
                "NOME, " +
                "EMAIL, " +
                "SENHA, " +
                "FOTO FROM USUARIO WHERE EMAIL = ? AND SENHA = ?";

        SQLiteDatabase banco = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { email, senha });
        if(cursor.moveToFirst()){
            usuario.setId(cursor.getInt(0));
            usuario.setNome(cursor.getString(1));
            usuario.setEmail(cursor.getString(2));
            usuario.setSenha(cursor.getString(3));
            byte[] blob = cursor.getBlob(4);
            if (blob != null)
                usuario.setFoto(BitmapFactory.decodeStream(new ByteArrayInputStream(cursor.getBlob(4))));
        }
        cursor.close();

        return usuario;
    }

    public int removerUsuario(Usuario usuario)
    {
        SQLiteDatabase banco = this.getWritableDatabase();

        return banco.delete(tblUsuarios, "ID = ?", new String[usuario.getId()]);

    }

    public int atualizarUsuario(Usuario usuario)
    {
        SQLiteDatabase banco = this.getWritableDatabase();

        //ByteArrayOutputStream stream = new ByteArrayOutputStream();
        //usuario.getFoto().compress(Bitmap.CompressFormat.PNG, 100, stream);
        //byte[] byteArray = stream.toByteArray();
        //usuario.getFoto().recycle();


        ContentValues cv = new ContentValues();
        cv.put("NOME",usuario.getNome()); //These Fields should be your String values of actual column names
        cv.put("EMAIL",usuario.getEmail());
        cv.put("SENHA",usuario.getSenha());

        //if (byteArray != null)
        //    cv.put("FOTO", byteArray);

        return banco.update(tblUsuarios, cv, "ID = " + usuario.getId(),null);
    }

    public boolean insertPonto(String dataHora, String latitude, String longitude, int tipo, String justificativa, int status, Usuario usuario)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("DATAHORA", dataHora);

        if (latitude != null)
            cv.put("LATITUDE", latitude);

        if (longitude != null)
            cv.put("LONGITUDE", longitude);

        cv.put("TIPO", tipo);

        if (justificativa != null)
            cv.put("JUSTIFICATIVA", justificativa);

        cv.put("STATUS", status);

        cv.put("USUARIOID", usuario.getId());

        long result = db.insert(tblPonto,null, cv);

        return result > 0;


    }

    public List<Ponto> buscarPontos(Usuario usuario, String dataHoraInicio, String dataHoraFim)
    {
        List<Ponto> pontos = new ArrayList<Ponto>();

        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT " +
                "ID," +
                "DATAHORA," +
                "LATITUDE," +
                "LONGITUDE, " +
                "TIPO," +
                "JUSTIFICATIVA," +
                "STATUS," +
                "USUARIOID " +
                "FROM " + tblPonto +
                " WHERE DATAHORA BETWEEN ? AND ? AND USUARIOID = ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[] { dataHoraInicio, dataHoraFim,  String.valueOf(usuario.getId())});

        while (cursor.moveToNext()){
            Ponto ponto = new Ponto();

            ponto.setId(cursor.getInt(0));

            SimpleDateFormat formato = new SimpleDateFormat("yyyyMMdd HHmmss");
            Date dataFormatada = null;
            try {
                String a = cursor.getString (1);
                dataFormatada = formato.parse(cursor.getString (1));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            ponto.setDataHora(dataFormatada);

            ponto.setLatitude(cursor.getString(2));
            ponto.setLongitude(cursor.getString(3));
            ponto.setTipo(cursor.getInt(4));
            ponto.setJustificativa(cursor.getString(5));
            ponto.setStatus(cursor.getInt(6));
            ponto.setUsuarioId(cursor.getInt(7));

            pontos.add(ponto);
        }
        cursor.close();

        return pontos;
    }

}
