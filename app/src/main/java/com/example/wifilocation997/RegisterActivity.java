package com.example.wifilocation997;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_reg_confirm;
    private EditText et_reg_username;
    private EditText et_reg_password;
    private View et_reg_confirmpwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btn_reg_confirm = findViewById(R.id.btn_reg_confirm);
        et_reg_username = findViewById(R.id.et_reg_username);
        et_reg_password = findViewById(R.id.et_reg_password);
        et_reg_confirmpwd = findViewById(R.id.et_reg_confirmpwd);

        btn_reg_confirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();
    }
}