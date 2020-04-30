package com.qoolqas.tokoroti.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.qoolqas.tokoroti.MainActivity;
import com.qoolqas.tokoroti.R;
import com.qoolqas.tokoroti.sqlite.DBDataSource;

public class LoginActivity extends AppCompatActivity {
    DBDataSource dataSource;
    TextInputLayout nama,password;
    TextView register;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dataSource = new DBDataSource(this);
        register = findViewById(R.id.txtRegister);
        login = findViewById(R.id.loginButton);
        nama = findViewById(R.id.loginEtName);
        password = findViewById(R.id.loginEtPassword);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateName()||validatePassword()){
                    cekUser();
                }
            }
        });
    }
    private boolean validateName() {
        String nameV = nama.getEditText().getText().toString().trim();

        if (nameV.isEmpty()) {
            nama.setError("Nama tidak boleh kosong");
            return false;
        } else {
            nama.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String pw = password.getEditText().getText().toString().trim();

        if (pw.isEmpty()) {
            password.setError("Password tidak boleh kosong");
            return false;
        } else {
            password.setError(null);
            return true;
        }
    }
    void cekUser() {
        if (dataSource.checkUser(nama.getEditText().getText().toString().trim()
                , password.getEditText().getText().toString().trim())) {


            Intent intent = new Intent(LoginActivity.this, MainActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("name", nama.getEditText().getText().toString());
            startActivity(intent);
            finish();


        } else {
            Toast.makeText(this, "Nama Atau Password Salah", Toast.LENGTH_LONG).show();
        }


    }
}
