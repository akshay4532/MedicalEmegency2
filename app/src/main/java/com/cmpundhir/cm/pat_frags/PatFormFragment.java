package com.cmpundhir.cm.pat_frags;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.cmpundhir.cm.OnFragmentInteractionListener;
import com.cmpundhir.cm.medicalemegency.R;
import com.cmpundhir.cm.medicalemegency.model.PatientData;
import com.cmpundhir.cm.medicalemegency.patient.PatientDetailsForm;
import com.cmpundhir.cm.medicalemegency.patient.PatientHomeActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tsongkha.spinnerdatepicker.DatePicker;
import com.tsongkha.spinnerdatepicker.DatePickerDialog;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class PatFormFragment extends Fragment {
    OnFragmentInteractionListener mListener;
    private static final String TAG = "PateintDetailsForm";
    Context context;
    @BindView(R.id.pat_fname)
    TextInputEditText patFname;
    @BindView(R.id.pat_mom)
    TextInputEditText patMname;
    @BindView(R.id.pat_dob)
    TextInputEditText patDoB;
    @BindView(R.id.pat_marr)
    RadioButton pat_marr;
    @BindView(R.id.pat_unmarried)
    RadioButton pat_unmarried;
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


    Button patSubmitBtn;

    @BindView(R.id.progressbarPatDetails)
    ProgressBar progressbar;

    private FirebaseAuth mAuth;
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Patient_Detail_Form");
    RelativeLayout relativeLayout;

    SimpleDateFormat simpleDateFormat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_pat_form, container, false);
      ButterKnife.bind(this,v);
        mAuth = FirebaseAuth.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        patSubmitBtn=v.findViewById(R.id.patSubmit_form_btn);
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
                    patFname.setError("Please Enter your Father Name");
                    return;
                }

                marry = null;
                switch (marStatus.getCheckedRadioButtonId()){
                    case R.id.pat_unmarried : marry = "unmarried";break;
                    case R.id.pat_marr : marry = "Married";break;
                }
                if (marry==null){
                    Toast.makeText(getActivity(), "Please select your Marital status", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(Ptmname)){
                    patMname.setError("Please Enter your Mother Name");
                    return;
                }

                if(TextUtils.isEmpty(ptdob)){
                    patDoB.requestFocus();
                    Toast.makeText(getActivity(), "Please Enter a dob", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(ptnation)){
                    patNation.setError("Please Enter a nation");
                    return;
                }
                if(TextUtils.isEmpty(ptlanguage)){
                    patlangspoken.setError("Please Enter a Language");
                    return;
                }
                if(TextUtils.isEmpty(ptblood)){
                    patblood.setError("Please Enter a blood group");
                    return;
                }
                if(TextUtils.isEmpty(ptflat)){
                    patflat.setError("Please Enter flat");
                    return;
                }
                if(TextUtils.isEmpty(ptcity)){
                    patcity.setError("Please Enter a city");
                    return;
                }
                if(TextUtils.isEmpty(ptcountry)){
                    patcountry.setError("Please Enter a country");
                    return;
                }
                if(TextUtils.isEmpty(ptroad)){
                    patroad.setError("Please Enter a road");
                    return;
                }
                if(TextUtils.isEmpty(ptlocality)){
                    patLocality.setError("Please Enter a locality");
                    return;
                }
                if(TextUtils.isEmpty(marry)){
                    marStatus.requestFocus();
                    Toast.makeText(getActivity(), "Please select a Marital status", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(ptweight)){
                    patweight.requestFocus();
                    Toast.makeText(getActivity(), "Please Select a Weight", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(pthieght)){
                    patheight.requestFocus();
                    Toast.makeText(getActivity(), "Please Select a Height", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(ptmedhidtory)){
                    patmedhistory.requestFocus();
                    Toast.makeText(getActivity(), "Please Select a MedicalHistory", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(ptnotes)){
                    patnotes.requestFocus();
                    Toast.makeText(getActivity(), "Please Select a MedNotes", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(ptpincode)){
                    patpincode.requestFocus();
                    Toast.makeText(getActivity(), "Please Select a Pincode", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(ptstate)){
                    patstate.requestFocus();
                    Toast.makeText(getActivity(), "Please Select a State", Toast.LENGTH_SHORT).show();
                    return;
                }

                regFormPateint(marry);
            }
        });
      return v;
    }
//    @OnClick(R.id.btn_nxt)
//    public void onNext(View view){
//        mListener.onFragmentInteraction(view);
//    }

    public void regFormPateint(final String marry){
        //final String pfname,final String pmnane, final String pdob, final String pnat, final String plan,
        //                           final String pblod, final String pflat, final String ploc, final String pcity, final String proad,
        //             final String pstate, final String pcount, final String ppin
        progressbar.setVisibility(View.VISIBLE);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                PatientData patientData = dataSnapshot.getValue(PatientData.class);
                addToDatabse(marry);
               // Toast.makeText(context, "data insert", Toast.LENGTH_SHORT).show();
                Toast.makeText(context, "Form Submitted Successfully :)", Toast.LENGTH_LONG).show();
                showDialog();
                progressbar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(context, "Form Not Submitted )", Toast.LENGTH_LONG).show();
            onNotSucessDialog();
            }
        });

    }
    @OnClick(R.id.pat_calenderbtn)
    public void calBtn(){
        new SpinnerDatePickerDialogBuilder()
                .context(getActivity())
                .callback(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar calendar = new GregorianCalendar(year, monthOfYear, dayOfMonth);
                        patDoB.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                })
                .spinnerTheme(R.style.NumberPickerStyle)
                .showTitle(true)
                .showDaySpinner(true)
                .defaultDate(2017, 0, 1)
                .maxDate(2020, 0, 1)
                .minDate(2000, 0, 1)
                .build()
                .show();
}
    public void showDialog(){
        final Dialog dialog = new Dialog(context,android.R.style.Theme_DeviceDefault_Dialog_Alert);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alertdialog_form);
        dialog.setCancelable(false);
        dialog.show();
        Button ok,kaata;
        ok=dialog.findViewById(R.id.ok_formPat);
        kaata=dialog.findViewById(R.id.cross);
        kaata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset(v);
                dialog.dismiss();
            }
        });
    }
    public void onNotSucessDialog(){
        final  Dialog dialog = new Dialog(context,android.R.style.Theme_DeviceDefault_Dialog_Alert);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alertdialog_formnotsucess);
        dialog.setCancelable(false);
        dialog.show();
        Button kaata ;
        ImageView ref;
        ref=dialog.findViewById(R.id.refreshForm);
        kaata=dialog.findViewById(R.id.cross);
        kaata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset(v);
                dialog.dismiss();
            }
        });
    }
    private void reset(View v){
        mListener.onFragmentInteraction(v);
    }

    public void addToDatabse(String marry){
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
        patientData.setPatimaried(marry);
        patientData.setPatState(patstate.getText().toString());
        myRef.child(firebaseUser.getUid()).setValue(patientData);

       // startActivity(new Intent(getActivity(), PatientHomeActivity.class));


    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
            this.context=context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }




}
