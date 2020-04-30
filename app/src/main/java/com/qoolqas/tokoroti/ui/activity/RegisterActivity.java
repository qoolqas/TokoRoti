package com.qoolqas.tokoroti.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.qoolqas.tokoroti.R;
import com.qoolqas.tokoroti.pojo.DataUser;
import com.qoolqas.tokoroti.sqlite.DBDataSource;

public class RegisterActivity extends AppCompatActivity {
    DBDataSource dataSource;
    TextInputLayout nama, password;
    Button register;
    private DataUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        dataSource = new DBDataSource(this);
        user = new DataUser();
        nama = findViewById(R.id.registerName);
        password = findViewById(R.id.registerPassword);
        register = findViewById(R.id.registerButton);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateName() || validatePassword()) {
                    createRegister();
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

    void createRegister() {
        String namaCheck = nama.getEditText().getText().toString().trim();
        Log.d("nama", namaCheck);
        if (!dataSource.checkName(namaCheck)) {
            user.setU_NAME(nama.getEditText().getText().toString().trim());
            user.setU_PASSWORD(password.getEditText().getText().toString().trim());
            dataSource.createUser(user);
            finish();
        } else {
            Toast.makeText(this, "Nama tidak boleh sama", Toast.LENGTH_LONG).show();
        }
    }
}
