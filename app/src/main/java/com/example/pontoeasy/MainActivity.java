package com.example.pontoeasy;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    androidx.appcompat.widget.Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Objeto Toolbar
        toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setElevation(10.f);
        }
    }

    //método que infla o menu no toolbar do aplicativo (res/menu/list_of_users_menu.xml)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    //realiza a ação de click de um dos itens do menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String msg = "";
        Intent intent;
        //verifica qual dos itens foi selecionado e atribui a string msg o titulo do item
        switch(item.getItemId()){
            case R.id.btnPerfil:
                intent = new Intent(this, PerfilActivity.class);
                startActivity(intent);
                break;
            case R.id.btnHistorico:
                intent = new Intent(this, HistoricoActivity.class);
                startActivity(intent);
                break;
            case R.id.btnEnviar:
                msg = "enviar";
                break;
            case R.id.btnLogout:
                msg = "logout";
                break;
        }

        Toast.makeText(this, msg + " checked", Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
    }
}
