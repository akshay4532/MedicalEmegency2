package com.cmpundhir.cm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cmpundhir.cm.medicalemegency.LoginActivity;
import com.cmpundhir.cm.medicalemegency.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForgotPassword extends AppCompatActivity {
    private TextInputEditText emailForgotPassword;
    private Button sendEmailForgotPassword;
    private FirebaseAuth mAuth;
    @BindView(R.id.progressbarForgot)
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_forgot_password);
        getSupportActionBar().hide();
        mAuth = FirebaseAuth.getInstance();
        ButterKnife.bind(this);
        emailForgotPassword = findViewById(R.id.emailAlrdyReg);
        sendEmailForgotPassword = findViewById(R.id.AlrdysendReg_btn);

        sendEmailForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String userEmail = emailForgotPassword.getText().toString();

                if(TextUtils.isEmpty(userEmail)){
                    Toast.makeText(ForgotPassword.this,"Enter Email",Toast.LENGTH_SHORT).show();
                }else{

                    mAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            progressBar.setVisibility(View.GONE);
                            if(task.isSuccessful()) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(ForgotPassword.this);
                                builder.setTitle("Email Sent ");
                                builder.setMessage("Email is Sent to your registerd Email_id");

                                AlertDialog dialog = builder.create();
                                dialog.show();
                                Toast.makeText(ForgotPassword.this, "Please check your Email Account for reset password.", Toast.LENGTH_LONG).show();

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        startActivity(new Intent(ForgotPassword.this, LoginActivity.class));
                                        finish();
                                    }
                                },1000);
                                finish();

                            }else{
                                String message = task.getException().getMessage();
                                Toast.makeText(ForgotPassword.this, message, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });

    }
}
