package com.cmpundhir.cm.medicalemegency;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.cmpundhir.cm.medicalemegency.R;
import com.cmpundhir.cm.medicalemegency.model.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginChoiceActivity extends AppCompatActivity {
    User user;
    @BindView(R.id.img)
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_choice);
        getSupportActionBar().hide();
        ButterKnife.bind(this);

    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        finish();
//        overridePendingTransition(0, 0);
//    }
    @OnClick(R.id.patient_log)
    public void loginAsPatient(View view){
       Intent intent=new Intent(this,LoginActivity.class);
       intent.putExtra("typePatienr",0);
       startActivity(intent);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginChoiceActivity.this,img,"imageTransition");
                Intent intent = new Intent(LoginChoiceActivity.this,LoginActivity.class);
                startActivity(intent,options.toBundle());
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                     //   finish();
                    }
                },1000);

            }
        },30);

    }
    @OnClick(R.id.doctor_log)
    public void loginAsDoctor(View view){
        Intent intent=new Intent(this,LoginActivity.class);
        intent.putExtra("typeDoctor",1);
        startActivity(intent);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginChoiceActivity.this,img,"imageTransition");
                Intent intent = new Intent(LoginChoiceActivity.this,LoginActivity.class);
                startActivity(intent,options.toBundle());
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //finish();
                    }
                },1000);

            }
        },30);

    }
    @OnClick(R.id.sign_up)
    public void Signup(View view){
        startActivity(new Intent(LoginChoiceActivity.this,RegistrationChoiceActivity.class));
    }

}
