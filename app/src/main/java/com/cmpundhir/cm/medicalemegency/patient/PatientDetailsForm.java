package com.cmpundhir.cm.medicalemegency.patient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.cmpundhir.cm.medicalemegency.LoginChoiceActivity;
import com.cmpundhir.cm.medicalemegency.PatientRegActivity;
import com.cmpundhir.cm.medicalemegency.R;
import com.cmpundhir.cm.medicalemegency.model.PatientData;
import com.cmpundhir.cm.medicalemegency.model.User;
import com.cmpundhir.cm.medicalemegency.utils.DatabaseStatus;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PatientDetailsForm extends AppCompatActivity {
    private static final String TAG = "PateintDetailsForm";
    @BindView(R.id.pat_fname)
    TextInputEditText patFname;
    @BindView(R.id.pat_mom)
    TextInputEditText patMname;
    @BindView(R.id.pat_dob)
    TextInputEditText patDoB;
    @BindView(R.id.marryStatus)
    RadioGroup marStatus;
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

    @BindView(R.id.progressbarPatDetails)
    ProgressBar progressbar;

   private FirebaseAuth mAuth;
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Patient_Detail_Form");
    RelativeLayout relativeLayout;

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
                        ,ptcity,ptstate,ptcountry,ptpincode,marry;
                Ptfname=patFname.getText().toString();
                Ptmname=patMname.getText().toString();
                ptdob=patDoB.getText().toString();
                ptnation=patNation.getText().toString();
                ptlanguage=patlangspoken.getText().toString();
                ptblood=patblood.getText().toString();
                ptflat=patflat.getText().toString();
                ptlocality=patLocality.getText().toString();
                ptcity=patcity.getText().toString();
                ptroad=patroad.getText().toString();
                ptstate=patstate.getText().toString();
                ptcountry=patcountry.getText().toString();
                ptpincode=patpincode.getText().toString();
                ptweight=patweight.getText().toString();
                pthieght=patheight.getText().toString();
                ptnotes=patnotes.getText().toString();
                ptmedhidtory=patmedhistory.getText().toString();

                if(TextUtils.isEmpty(patFname.getText().toString())){
                   patFname.setError("Please enter your Father Name");
                    return;
                }
                try {
                   marry = ((RadioButton) findViewById(marStatus.getCheckedRadioButtonId())).getText().toString();
                }catch (NullPointerException e){
                    Toast.makeText(PatientDetailsForm.this, "Please select your Marital status", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(Ptmname)){
                   patMname.setError("Please enter your Mother Name");
                    return;
                }

                if(TextUtils.isEmpty(ptdob)){
                    patDoB.requestFocus();
                    Toast.makeText(PatientDetailsForm.this, "Please enter a dob", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(ptnation)){
                    patNation.setError("Please enter a nation");
                    return;
                }
                if(TextUtils.isEmpty(ptlanguage)){
                    patlangspoken.setError("Please enter a Language");
                    return;
                }
                if(TextUtils.isEmpty(ptblood)){
                    patblood.setError("Please enter a blood group");
                    return;
                }
                if(TextUtils.isEmpty(ptflat)){
                    patflat.setError("Please enter flat");
                    return;
                }
                if(TextUtils.isEmpty(ptcity)){
                   patcity.setError("Please enter a city");
                    return;
                }
                if(TextUtils.isEmpty(ptcountry)){
                    patcountry.setError("Please enter a country");
                    return;
                }
                if(TextUtils.isEmpty(ptroad)){
                    patroad.setError("Please enter a road");
                    return;
                }
                if(TextUtils.isEmpty(ptlocality)){
                    patLocality.setError("Please enter a locality");
                    return;
                }
                if(TextUtils.isEmpty(marry)){
                   marStatus.requestFocus();
                    Toast.makeText(PatientDetailsForm.this, "Please select a Marital status", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(ptweight)){
                    patweight.requestFocus();
                    Toast.makeText(PatientDetailsForm.this, "Please Select a Weight", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(pthieght)){
                   patheight.requestFocus();
                    Toast.makeText(PatientDetailsForm.this, "Please Select a Height", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(ptmedhidtory)){
                    patmedhistory.requestFocus();
                    Toast.makeText(PatientDetailsForm.this, "Please Select a MedicalHistory", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(ptnotes)){
                    patnotes.requestFocus();
                    Toast.makeText(PatientDetailsForm.this, "Please Select a MedNotes", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(ptpincode)){
                    patpincode.requestFocus();
                    Toast.makeText(PatientDetailsForm.this, "Please Select a Pincode", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(ptstate)){
                    patstate.requestFocus();
                    Toast.makeText(PatientDetailsForm.this, "Please Select a State", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(PatientDetailsForm.this, "Form Submitted Sucessfully :)", Toast.LENGTH_LONG).show();
                regFormPateint();

            }
        });

    }
    public void regFormPateint(){
        //final String pfname,final String pmnane, final String pdob, final String pnat, final String plan,
        //                           final String pblod, final String pflat, final String ploc, final String pcity, final String proad,
        //             final String pstate, final String pcount, final String ppin
        progressbar.setVisibility(View.VISIBLE);
           myRef.addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   PatientData patientData = dataSnapshot.getValue(PatientData.class);
                addToDatabse();
                   Toast.makeText(PatientDetailsForm.this, "data insert", Toast.LENGTH_SHORT).show();
               }

               @Override
               public void onCancelled(@NonNull DatabaseError databaseError) {

               }
           });

    }


    public void addToDatabse(){
       PatientData patientData = new PatientData();
        patientData.setPatiFatherName(patFname.getText().toString());
        patientData.setPatiMotherName(patMname.getText().toString());
        patientData.setPatDob(patDoB.getText().toString());
        patientData.setPatLanguagespoken(patlangspoken.getText().toString());
        patientData.setPatBloodgroup(patblood.getText().toString());
        patientData.setPatFlatNoAndBLDGName(patflat.getText().toString());
        patientData.setPatAreaLocality(patLocality.getText().toString());
        patientData.setPatCity(patcity.getText().toString());
        patientData.setPatCountry(patcountry.getText().toString());
        patientData.setPatPinCode(patpincode.getText().toString());
        patientData.setPatRoadNoName(patroad.getText().toString());
        patientData.setPatNationality(patNation.getText().toString());
        patientData.setPatPastmedicalhistory(patmedhistory.getText().toString());
        patientData.setPatClinicalnotes(patnotes.getText().toString());
        patientData.setPatState(patstate.getText().toString());
      //  patientData.setPatimaried(marStatus.getCheckedRadioButtonId().getText().toString();
        patientData.setPatState(patstate.getText().toString());
        myRef.child(firebaseUser.getUid()).setValue(patientData);

        startActivity(new Intent(PatientDetailsForm.this, PatientHomeActivity.class));




    }

}
