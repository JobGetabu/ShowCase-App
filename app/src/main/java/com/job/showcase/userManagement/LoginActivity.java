package com.job.showcase.userManagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.job.showcase.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.button_login)
    Button loginBtn;
    @BindView(R.id.button_register)
    Button registerBtn;
    @BindView(R.id.text_password)
    TextView tv_password;
    @BindView(R.id.login_email)
    TextView tv_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
    }
    @OnClick(R.id.button_register)
    public void sendToRegister(){
        Intent intent = new Intent(this,RegisterActivity.class);
        finish();
        startActivity(intent);
    }

    @OnClick(R.id.button_login)
    public void sendBackTologin(){
        finish();
    }
}
