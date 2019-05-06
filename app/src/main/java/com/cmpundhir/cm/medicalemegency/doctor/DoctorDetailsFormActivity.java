package com.cmpundhir.cm.medicalemegency.doctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.cmpundhir.cm.medicalemegency.LoginChoiceActivity;
import com.cmpundhir.cm.medicalemegency.R;

public class DoctorDetailsFormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details_form);
        getSupportActionBar().hide();
//        startActivity(new Intent(DoctorDetailsFormActivity.this, LoginChoiceActivity.class));
//        finish();
    }
}
