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
import com.cmpundhir.cm.medicalemegency.utils.DatabaseStatus;

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
       intent.putExtra("type", DatabaseStatus.USER_TYPE_PATIENT);
       startActivity(intent);
    }
    @OnClick(R.id.doctor_log)
    public void loginAsDoctor(View view){
        Intent intent=new Intent(this,LoginActivity.class);
        intent.putExtra("type", DatabaseStatus.USER_TYPE_DOCTOR);
        startActivity(intent);
    }
    @OnClick(R.id.sign_up)
    public void Signup(View view){
        startActivity(new Intent(LoginChoiceActivity.this,RegistrationChoiceActivity.class));
    }

}
