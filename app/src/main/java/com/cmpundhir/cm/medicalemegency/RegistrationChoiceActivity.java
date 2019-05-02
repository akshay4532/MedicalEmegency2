package com.cmpundhir.cm.medicalemegency;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegistrationChoiceActivity extends AppCompatActivity {

    ImageView img;
    @BindView(R.id.patient_registeration)
    Button patientButton;

    @BindView(R.id.doctor_registeration)
    Button doctorButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_choice);
        getSupportActionBar().hide();
        ButterKnife.bind(this);

    }

    @OnClick(R.id.patient_registeration)
    public void onPatientRegClick(View view){
        startActivity(new Intent(RegistrationChoiceActivity.this,PatientRegActivity.class));
    }

    @OnClick(R.id.doctor_registeration)
    public void onDoctorRegClick(View view){
        startActivity(new Intent(RegistrationChoiceActivity.this,DoctorRegActivity.class));

    }
}
