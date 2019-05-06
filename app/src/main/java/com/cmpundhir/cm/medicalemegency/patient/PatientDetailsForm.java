package com.cmpundhir.cm.medicalemegency.patient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import com.cmpundhir.cm.medicalemegency.LoginChoiceActivity;
import com.cmpundhir.cm.medicalemegency.PatientRegActivity;
import com.cmpundhir.cm.medicalemegency.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PatientDetailsForm extends AppCompatActivity {
    @BindView(R.id.pat_fname)
    TextInputEditText patFname;
    @BindView(R.id.pat_mom)
    TextInputEditText patMname;
    @BindView(R.id.pat_dob)
    TextInputEditText patDoB;
    @BindView(R.id.pat_marr)
    RadioButton patMar;
    @BindView(R.id.pat_unmarried)
    RadioButton patUnm;
    @BindView(R.id.pat_Nation)
    TextInputEditText patNation;
    @BindView(R.id.pat_langspoken)
    TextInputEditText patlangspoken;
    @BindView(R.id.pat_medHistory)
    TextInputEditText patmedhistory;
    @BindView(R.id.pat_blood)
    TextInputEditText patblood;
    @BindView(R.id.pat_height)
    TextInputEditText patheight;
    @BindView(R.id.pat_weigh)
    TextInputEditText patweight;
    @BindView(R.id.pat_notes)
    TextInputEditText patnotes;
    @BindView(R.id.pat_flat)
    TextInputEditText patflat;
    @BindView(R.id.pat_road)
    TextInputEditText patroad;
    @BindView(R.id.pat_Locality)
    TextInputEditText patLocality ;
    @BindView(R.id.pat_city)
    TextInputEditText patcity ;
    @BindView(R.id.pat_state)
    TextInputEditText patstate;
    @BindView(R.id.pat_country)
    TextInputEditText patcountry;
    @BindView(R.id.pat_pin)
    TextInputEditText patpincode;
    @BindView(R.id.patSubmit_btn)
    Button patSubmitBtn;
    private FirebaseAuth mAuth;
    @BindView(R.id.progressbarPat)
    ProgressBar progressbar;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("patients");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_details_form);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();
        patSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Ptfname,Ptmname,ptdob,ptmarried,ptunmarried,ptnation,ptlanguage
                        ,ptmedhidtory,ptblood,pthieght,ptweight,ptnotes,ptflat,ptroad,ptlocality
                        ,ptcity,ptstate,ptcountry,ptpincode;
                Ptfname=patFname.getText().toString();
                Ptmname=patMname.getText().toString();
                ptdob=patDoB.getText().toString();
                ptmarried=patMar.getText().toString();
                ptunmarried=patUnm.getText().toString();
                ptnation=patNation.getText().toString();
                ptlanguage=patlangspoken.getText().toString();
                ptblood=patblood.getText().toString();
                ptflat=patflat.getText().toString();
                ptlocality=patLocality.getText().toString();
                ptcity=patcity.getText().toString();
                ptstate=patstate.getText().toString();
                ptcountry=patcountry.getText().toString();
                ptpincode=patpincode.getText().toString();

//                if(TextUtils.isEmpty(patFname.getText().toString())){
//                   patFname.setError("Please enter your name");
//                    return;
//                }
//                try {
//                    patMar = ((RadioButton) findViewById(patMar.getCheckedRadioButtonId())).getText().toString();
//                }catch (NullPointerException e){
//                    Toast.makeText(PatientDetailsForm.this, "Please select your Marri status", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if(TextUtils.isEmpty(email)){
//                    emailEdit.setError("Please enter your email");
//                    return;
//                }
//
//                if(TextUtils.isEmpty(genderr)){
//                    gender.requestFocus();
//                    Toast.makeText(PatientRegActivity.this, "Please select your gender", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if(TextUtils.isEmpty(phone)){
//                    mob.setError("Please enter mobile number");
//                    return;
//                }
//                if(TextUtils.isEmpty(pss)){
//                    pass.setError("Please enter a strong password");
//                    return;
//                }




            }
        });

    }

}
