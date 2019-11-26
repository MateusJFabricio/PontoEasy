package com.example.pontoeasy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pontoeasy.Controller.BancoDados;
import com.example.pontoeasy.DAO.Ponto;
import com.example.pontoeasy.DAO.Usuario;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class ExportarActivity extends AppCompatActivity {

    private Button btnExportar;
    private TextView txtEmailDestino;
    private Usuario usuario;
    private Date dataInicio, dataFim;
    private BancoDados bancoDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exportar);

        bancoDados = new BancoDados(this, "", null, 0);

        btnExportar = findViewById(R.id.btnExportar);
        txtEmailDestino = findViewById(R.id.txtEmailDestino);

        Intent intent = getIntent();
        usuario = (Usuario) intent.getSerializableExtra("Usuario");
        dataInicio = (Date) intent.getSerializableExtra("DataInicio");
        dataFim = (Date) intent.getSerializableExtra("DataFim");

    }

    public void btnExportarEmai_OnClick(View view) {
        if (txtEmailDestino.getText().toString().length() <= 0)
            return;

        sendEmail(txtEmailDestino.getText().toString(), buscarPontos());
    }

    protected void sendEmail(String emailDestino, String pontos) {
        String[] TO = {"fabriciomateus05@gmail.com"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("Historico de ponto");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Historico de ponto");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Em anexo");
        try {
            startActivity(Intent.createChooser(emailIntent, pontos));
            finish();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this,
                    "No tienes clientes de email instalados.", Toast.LENGTH_SHORT).show();
        }
    }


    private String buscarPontos()
    {
        String resultado = "";


        List<Ponto> pontos = bancoDados.buscarPontos(usuario, getDateTime(dataInicio), getDateTime(dataFim));

        for (Ponto ponto:pontos) {
            resultado+= "Data: " + ponto.getDataHora().toString() + " - ";

            if (ponto.getTipo() == 1)
                resultado+= "Entrada - ";
            else
                resultado+= "Saida - ";

            resultado+= "Justificativa: " + ponto.getJustificativa() + " - ";
            resultado+= "Localizacao: " + ponto.getLatitude() + " - " + ponto.getLongitude() + " - ";

        }

        return resultado;
    }

    private String getDateTime(Date d) {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HHmmss");
        return dateFormat.format(d);
    }

}
