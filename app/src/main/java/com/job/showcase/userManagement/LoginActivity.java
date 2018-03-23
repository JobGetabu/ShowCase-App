package com.job.showcase.userManagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.job.showcase.MainActivity;
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
    @BindView(R.id.login_progressbar)
    ProgressBar progressBar;

    FirebaseAuth mAuth;

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            sendToMain();
        }
    }

    private void sendToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
    }
    @OnClick(R.id.button_register)
    public void sendToRegister(){
        Intent intent = new Intent(this,RegisterActivity.class);
        finish();
        startActivity(intent);
    }

    @OnClick(R.id.button_login)
    public void loginTheUser(){

        String email = tv_email.getText().toString();
        String password = tv_password.getText().toString();
        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> logintask) {
                        if (logintask.isSuccessful()){
                            progressBar.setVisibility(View.INVISIBLE);
                            sendToMain();
                        }else {
                            //Custom error handling
                            Toast.makeText(LoginActivity.this,
                                    "Error "+logintask.getException(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                });
    }
}
