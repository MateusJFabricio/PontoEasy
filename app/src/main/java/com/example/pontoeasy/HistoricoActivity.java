package com.example.pontoeasy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pontoeasy.Controller.BancoDados;
import com.example.pontoeasy.DAO.Ponto;
import com.example.pontoeasy.DAO.Usuario;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class HistoricoActivity extends AppCompatActivity {

    private BancoDados bancoDados;
    private Usuario usuario;
    private LinearLayout linearLayout;
    private TextView txtDataInicio, txtDataFim;
    private List<Ponto> pontos;
    private Date dataInicio = null;
    private Date dataFim = null;


    private List<TextView> listaPontos = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);

        linearLayout = findViewById(R.id.linearLayout);
        txtDataInicio = findViewById(R.id.txtDataInicio);
        txtDataFim = findViewById(R.id.txtDataFim);
        bancoDados = new BancoDados(this, "", null, 0);

        Intent intent = getIntent();
        usuario = (Usuario) intent.getSerializableExtra("Usuario");

        buscarUltimos30Dias();
    }

    private void buscarUltimos30Dias()
    {
        GregorianCalendar c = new GregorianCalendar();
        c.add(Calendar.MONTH, -1);
        dataInicio = c.getTime();
        dataFim = new Date();
         pontos = bancoDados.buscarPontos(usuario, getDateTime(c.getTime()), getDateTime(new Date()));

        for (Ponto ponto:pontos) {

            String data = getDate(ponto.getDataHora());
            String tipo;
            if (ponto.getTipo() == 1)
                tipo = "Entrada";
            else
                tipo = "Saida";

            TextView textView = new TextView(this);
            textView.setText(getDate(ponto.getDataHora()) + " - " + tipo);
            textView.setTag(ponto);
            textView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Ponto ponto = (Ponto) v.getTag();
                            AbrirEdicaoPonto(ponto);
                        }
                    }
            );
            linearLayout.addView(textView);
        }
    }

    private void AbrirEdicaoPonto(Ponto ponto) {
        Intent intent = new Intent(this, EditarPontoActivity.class);
        intent.putExtra("Usuario", usuario);
        intent.putExtra("Ponto", ponto);
        startActivity(intent);
    }


    private String getDate(Date d) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return dateFormat.format(d);
    }

    private String getDateTime(Date d) {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HHmmss");
        return dateFormat.format(d);
    }

    public void btnBuscarHistorico_OnClick(View view) {
        linearLayout.removeAllViews();
        GregorianCalendar c = new GregorianCalendar();
        c.add(Calendar.MONTH, -1);

        dataInicio = null;
        dataFim = null;
        try {
            SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
            dataInicio = formatoData.parse(txtDataInicio.getText().toString() );
            dataFim = formatoData.parse(txtDataFim.getText().toString() );
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(this, "Problema com a data", Toast.LENGTH_LONG).show();
            return;
        }

        pontos = bancoDados.buscarPontos(usuario, getDateTime(dataInicio), getDateTime(dataFim));

        for (Ponto ponto:pontos) {

            String data = getDate(ponto.getDataHora());
            String tipo;
            if (ponto.getTipo() == 1)
                tipo = "Entrada";
            else
                tipo = "Saida";

            TextView textView = new TextView(this);
            textView.setText(getDate(ponto.getDataHora()) + " - " + tipo);
            textView.setTag(ponto);
            textView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Ponto ponto = (Ponto) v.getTag();
                            AbrirEdicaoPonto(ponto);
                        }
                    }
            );
            linearLayout.addView(textView);
        }
    }

    public void btnExportarHist_OnClick(View view) {

        if (dataFim != null && dataInicio != null) {
            Intent intent = new Intent(this, ExportarActivity.class);
            intent.putExtra("Usuario", usuario);
            intent.putExtra("DataInicio", dataInicio);
            intent.putExtra("DataFim", dataFim);
            startActivity(intent);
        }
    }
}
