package com.cmpundhir.cm.medicalemegency;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.cmpundhir.cm.medicalemegency.doctor.DoctorDetailsFormActivity;
import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DoctorRegActivity extends AppCompatActivity {


    @BindView(R.id.specialist)
    Spinner category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_reg);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        String[] str = getResources().getStringArray(R.array.category);
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,str);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(adapter);
    }

    @OnClick(R.id.button2)
    public void onButton2Clicked(View view){
        Intent intent = new Intent(DoctorRegActivity.this, DoctorDetailsFormActivity.class);
        startActivity(intent);
    }
}
