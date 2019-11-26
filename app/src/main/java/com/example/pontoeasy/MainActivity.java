package com.example.pontoeasy;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pontoeasy.Controller.BancoDados;
import com.example.pontoeasy.DAO.Usuario;
import com.example.pontoeasy.ui.login.LoginActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.pontoeasy.R.drawable.avatar;

public class MainActivity extends AppCompatActivity {
    androidx.appcompat.widget.Toolbar toolbar;
    private Usuario usuario;
    TextView txtNomePrincipal, txtLocalizacao;
    Button btnEntrada, btnSaida;
    private String latitude, longitude;
    private BancoDados bancoDados;
    private ImageView imagem;

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

        Intent intent = getIntent();
        usuario = (Usuario) intent.getSerializableExtra("Usuario");

        txtNomePrincipal = findViewById(R.id.txtNomePrincipal);
        txtLocalizacao = findViewById(R.id.txtLocalizacao);
        btnEntrada = findViewById(R.id.btnEntrada);
        btnSaida = findViewById(R.id.btnSaida);
        txtNomePrincipal.setText("Ola " + usuario.getNome());


        bancoDados = new BancoDados(this, "", null, 0);
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
                intent.putExtra ("Usuario", usuario);
                startActivity(intent);
                break;
            case R.id.btnHistorico:
                intent = new Intent(this, HistoricoActivity.class);
                intent.putExtra ("Usuario", usuario);
                startActivity(intent);
                break;
            case R.id.btnLogout:
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
        }


        return super.onOptionsItemSelected(item);
    }

    public void btnEntrada_onClick(View view) {
        Intent intent = new Intent(this, ConfirmarPontoActivity.class);
        intent.putExtra ("Usuario", usuario);
        intent.putExtra("TipoPonto", 1);
        startActivity(intent);
    }

    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public void btnSaida_onClick(View view) {

        Intent intent = new Intent(this, ConfirmarPontoActivity.class);
        intent.putExtra ("Usuario", usuario);
        intent.putExtra("TipoPonto", 2);
        startActivity(intent);

    }
}
