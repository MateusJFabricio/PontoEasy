package com.example.pontoeasy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pontoeasy.Controller.BancoDados;
import com.example.pontoeasy.DAO.Usuario;

import java.io.FileNotFoundException;
import java.lang.ref.WeakReference;

public class PerfilActivity extends AppCompatActivity {

    Button btnSalvarPerfil;
    TextView txtNome, txtEmail, txtSenha;
    ImageView ivFoto;
    BancoDados bancoDados;
    Usuario usuario;
    private static final int IMAGE_GALLERY_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        btnSalvarPerfil = findViewById(R.id.btnSalvarPerfil);
        txtNome = findViewById(R.id.txtNomePrincipal);
        txtEmail = findViewById(R.id.txtEmail);
        txtSenha = findViewById(R.id.txtSenha);
        //ivFoto = findViewById(R.id.ivFoto);

        Intent intent = getIntent();
        usuario = (Usuario) intent.getSerializableExtra("Usuario");
        carregarInformacoesEmTela();

        bancoDados = new BancoDados(this, "", null, 0);
    }

    public void carregarInformacoesEmTela()
    {
        txtSenha.setText(usuario.getSenha());
        txtEmail.setText(usuario.getEmail());
        txtNome.setText(usuario.getNome());
        //ivFoto.setImageBitmap(usuario.getFoto());
    }

    public void AtualizarObjeto()
    {
        usuario.setSenha(txtSenha.getText().toString());
        usuario.setEmail(txtEmail.getText().toString());
        usuario.setNome(txtNome.getText().toString());
        //ivFoto.buildDrawingCache();
        //usuario.setFoto(ivFoto.getDrawingCache());
    }

    public void btnSalvarPerfil_onClick(View view) {

        AtualizarObjeto();
        bancoDados.atualizarUsuario(usuario);

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra ("Usuario", usuario);
        startActivity(intent);
    }

    public void btnFoto_OnClick(View view) {


    }

}
