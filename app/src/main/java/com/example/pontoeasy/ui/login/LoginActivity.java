package com.example.pontoeasy.ui.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pontoeasy.Controller.BancoDados;
import com.example.pontoeasy.DAO.Usuario;
import com.example.pontoeasy.MainActivity;
import com.example.pontoeasy.PerfilActivity;
import com.example.pontoeasy.R;

public class LoginActivity extends AppCompatActivity {

    BancoDados bancoDados;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bancoDados = new BancoDados(this, "", null, 0);
    }

    private void acessarActivityMain(String email, String password)
    {
        Usuario usuario = bancoDados.buscarUsuario(email, password);

        if (usuario.getId() > 0)
        {
            Intent intent = new Intent(this, MainActivity.class);
            usuario.setFoto(null);
            intent.putExtra ("Usuario", usuario);
            startActivity(intent);
        }else
        {
            usuario = bancoDados.insertUsuario("inserir nome", email, password, null);
            Intent intent = new Intent(this, PerfilActivity.class);
            intent.putExtra ("Usuario", usuario);
            startActivity(intent);
        }
    }


    public void btnLogin_OnClick(View view) {
        TextView txtSenha = findViewById(R.id.password);
        TextView txtEmail = findViewById(R.id.username);

        //Validar senha
        if (txtSenha.getText().length() < 5)
        {
            Toast.makeText(this, "A senha deve ter mais que 5 caracteres", Toast.LENGTH_SHORT).show();
            return;
        }

        //Validar email
        if (!txtEmail.getText().toString().contains("@"))
        {
            Toast.makeText(this, "Email invalido", Toast.LENGTH_SHORT).show();
            return;
        }

        //Validar email
        if (!txtEmail.getText().toString().contains("."))
        {
            Toast.makeText(this, "Email invalido", Toast.LENGTH_SHORT).show();
            return;
        }

        acessarActivityMain(txtEmail.getText().toString(), txtSenha.getText().toString());
    }
}
