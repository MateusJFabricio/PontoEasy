package com.example.pontoeasy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.pontoeasy.Controller.BancoDados;
import com.example.pontoeasy.DAO.Ponto;
import com.example.pontoeasy.DAO.Usuario;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditarPontoActivity extends AppCompatActivity {

    private BancoDados bancoDados;
    private Ponto ponto;
    private Usuario usuario;
    private TextView txtData, txtHora;
    private RadioButton rbEntrada, rbSaida;
    private Button btnSalvar, btnCancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_ponto);

        btnCancelar = findViewById(R.id.btnCancelarEdicaoPonto);
        btnSalvar = findViewById(R.id.btnSalvarEdicaoPonto);

        txtData = findViewById(R.id.txtDataEdicaoPonto);
        txtHora = findViewById(R.id.txtHoraEdicaoPonto);
        rbEntrada = findViewById(R.id.rbEntrada);
        rbSaida = findViewById(R.id.rbSaida);

        bancoDados = new BancoDados(this, "", null, 0);
        Intent intent = getIntent();
        ponto = (Ponto) intent.getSerializableExtra("Ponto");
        usuario = (Usuario) intent.getSerializableExtra("Usuario");

        carregarInformacoes();
    }

    private void carregarInformacoes() {
        rbEntrada.setChecked(ponto.getTipo() == 1);
        rbSaida.setChecked(ponto.getTipo() == 2);

        txtData.setText(getDate(ponto.getDataHora()));
        txtHora.setText(getTime(ponto.getDataHora()));
    }

    private String getDate(Date d) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(d);
    }

    private String getTime(Date d) {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        return dateFormat.format(d);
    }

    private String getDateTime(Date d) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return dateFormat.format(d);
    }

    public void btnCancelarEditar_OnClick(View view) {
        Intent intent = new Intent(this, HistoricoActivity.class);
        intent.putExtra("Usuario", usuario);
        startActivity(intent);
    }

    public void btnSalvarEdicaoPontos_OnClick(View view) {
        Date date;
        try {
            date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(
                    txtData.getText().toString() + " " +
                            txtHora.getText().toString());

            ponto.setDataHora(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int tipo;
        if (rbEntrada.isChecked())
            tipo = 1;
        else
            tipo = 2;

        bancoDados.insertPonto(
                getDateTime(ponto.getDataHora()),
                ponto.getLatitude(),
                ponto.getLongitude(),
                tipo,
                ponto.getJustificativa(),
                2, //Ponto editado
                usuario);

        Intent intent = new Intent(this, HistoricoActivity.class);
        intent.putExtra("Usuario", usuario);
        startActivity(intent);
    }
}
