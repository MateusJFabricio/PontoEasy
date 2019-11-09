package com.example.pontoeasy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PerfilActivity extends AppCompatActivity {

    Button btnPerfil;
    BancoDados bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        btnPerfil = findViewById(R.id.btnPerfil);
        bd = new BancoDados(this, )
    }


    public void btnSalvarPerfil_onClick(View view) {


        bd.insertDB()
    }
}
