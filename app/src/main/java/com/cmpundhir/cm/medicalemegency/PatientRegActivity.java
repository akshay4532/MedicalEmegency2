package com.cmpundhir.cm.medicalemegency;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioGroup;

import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;

public class PatientRegActivity extends AppCompatActivity {

    @BindView(R.id.fname)
    TextInputEditText fname;
    @BindView(R.id.lname)
    TextInputEditText lname;
    @BindView(R.id.email)
    TextInputEditText emailEdit;
    @BindView(R.id.pass)
    TextInputEditText pass;
    @BindView(R.id.gender)
    RadioGroup gender;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_reg);
        getSupportActionBar().hide();

    }
}
