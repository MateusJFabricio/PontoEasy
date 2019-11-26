package com.example.pontoeasy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.pontoeasy.Controller.BancoDados;
import com.example.pontoeasy.DAO.Usuario;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConfirmarPontoActivity extends AppCompatActivity {
    private BancoDados bancoDados;
    private Usuario usuario;
    private int tipoPonto;
    private TextView txtData, txtHorario, txtLocalizacao, txtDescricao, txtTipoPonto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_ponto);
        bancoDados = new BancoDados(this, "", null, 0);

        Intent intent = getIntent();
        usuario = (Usuario) intent.getSerializableExtra("Usuario");
        tipoPonto = intent.getIntExtra("TipoPonto", 0);

        txtData = findViewById(R.id.txtDataEdicaoPonto);
        txtHorario = findViewById(R.id.txtHorario);
        txtLocalizacao = findViewById(R.id.txtLocalizacao);
        txtDescricao = findViewById(R.id.txtDescricao);
        txtTipoPonto = findViewById(R.id.txtTipoPonto);

        txtData.setText(getDate());
        txtHorario.setText(getTime());
        txtLocalizacao.setText("Ainda tem que colocar");
        txtDescricao.setText("Teste de descricao");

        if (tipoPonto == 1)
            txtTipoPonto.setText("Entrada");
        else
            txtTipoPonto.setText("Saida");
    }

    public void btnCancelarConfirmacaoPonto_OnClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra ("Usuario", usuario);
        startActivity(intent);
    }

    public void btnConfirmarConfirmacaoPonto_OnClick(View view) {
        String latitude = "12", longitude = "12";
        bancoDados.insertPonto(getDateTime(), latitude, longitude, tipoPonto, "", 1, usuario);

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra ("Usuario", usuario);
        startActivity(intent);
    }

    private String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }

    private String getTime() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HHmmss");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
