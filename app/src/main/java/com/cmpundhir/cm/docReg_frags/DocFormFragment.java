package com.cmpundhir.cm.docReg_frags;

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
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.cmpundhir.cm.OnFragmentInteractionListener;
import com.cmpundhir.cm.medicalemegency.R;
import com.cmpundhir.cm.medicalemegency.doctor.DoctorDetailsFormActivity;
import com.cmpundhir.cm.medicalemegency.doctor.DoctorHome2Activity;
import com.cmpundhir.cm.medicalemegency.model.DoctorData;
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


public class DocFormFragment extends Fragment {

    private static final String TAG = "DoctorDetailsForm";
    OnFragmentInteractionListener mListener;
    Context context;
    @BindView(R.id.doc_fname)
    TextInputEditText docFname;
    @BindView(R.id.doc_mom)
    TextInputEditText docMname;
    @BindView(R.id.doc_dob)
    TextInputEditText docDoB;
    @BindView(R.id.doc_Nation)
    TextInputEditText docNation;
    @BindView(R.id.doc_langspoken)
    TextInputEditText doclangspoken;
    @BindView(R.id.doc_medHistory)
    TextInputEditText docmedhistory;
    @BindView(R.id.doc_blood)
    TextInputEditText docblood;
    @BindView(R.id.doc_flat)
    TextInputEditText docflat;
    @BindView(R.id.doc_road)
    TextInputEditText docroad;
    @BindView(R.id.doc_Locality)
    TextInputEditText docLocality ;
    @BindView(R.id.doc_city)
    TextInputEditText doccity ;
    @BindView(R.id.doc_state)
    TextInputEditText docstate;
    @BindView(R.id.doc_country)
    TextInputEditText doccountry;
    @BindView(R.id.doc_pin)
    TextInputEditText docpincode;
    @BindView(R.id.doc_reg)
    TextInputEditText docReg;
    @BindView(R.id.specialist_doc)
    Spinner docSpin;
    @BindView(R.id.doc_qual)
    TextInputEditText docQual;
    @BindView(R.id.doc_insti)
    TextInputEditText docInsti;
    @BindView(R.id.doc_wrkingInsti)
    TextInputEditText docWrkngInsti;
    @BindView(R.id.doc_certificate)
    TextInputEditText docCertifi;
    @BindView(R.id.doc_calenderbtn)
    Button docCalbtn;
    @BindView(R.id.docSubmit_btn)
    Button docSubmitBtn;
    @BindView(R.id.doc_exp)
    TextInputEditText docExp;

    @BindView(R.id.progressbarDetaiDoctor)
    ProgressBar progressbar;
    SimpleDateFormat simpleDateFormat;
    private FirebaseAuth mAuth;
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Doctors_Detail_Form");
    RelativeLayout relativeLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final  View v =  inflater.inflate(R.layout.fragment_doc_form, container, false);
        ButterKnife.bind(this,v);
        mAuth = FirebaseAuth.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        docSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String docfname,docmname,docdob,docnation,doclanguage,dmedhidtory,
                        dblood,dflat,droad,doclocality,dcity,dstate,dcountry,dpincode
                        ,dreg,dqual,dinsti,dcurrInsti,dexp,dcertifi,dspin;
                docfname=docFname.getText().toString();
                docmname=docMname.getText().toString();
                docdob=docDoB.getText().toString();
                docnation=docNation.getText().toString();
                doclanguage=doclangspoken.getText().toString();
                dblood=docblood.getText().toString();
                dflat=docflat.getText().toString();
                doclocality=docLocality.getText().toString();
                dcity=doccity.getText().toString();
                droad=docroad.getText().toString();
                dstate=docstate.getText().toString();
                dcountry=doccountry.getText().toString();
                dreg=docReg.getText().toString();
                dpincode=docpincode.getText().toString();
                dmedhidtory=docmedhistory.getText().toString();
                dqual=docQual.getText().toString();
                dinsti=docInsti.getText().toString();
                dcurrInsti=docExp.getText().toString();
                dcertifi=docCertifi.getText().toString();
                dspin=docSpin.getSelectedItem().toString();

                if(TextUtils.isEmpty(docFname.getText().toString())){
                    docFname.setError("Please Enter your name");
                    return;
                }
                if(TextUtils.isEmpty(docmname)){
                    docMname.setError("Please Enter your Mother name");
                    return;
                }

                if(TextUtils.isEmpty(docdob)){
                    docDoB.requestFocus();
                    Toast.makeText(getActivity(), "Please Enter your Dob", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(docnation)){
                    docNation.setError("Please Enter a Nation");
                    return;
                }
                if(TextUtils.isEmpty(doclanguage)){
                    doclangspoken.setError("Please Enter a Language");
                    return;
                }
                if(TextUtils.isEmpty(dblood)){
                    docblood.setError("Please Enter a Blood Group");
                    return;
                }
                if(TextUtils.isEmpty(dflat)){
                    docflat.setError("Please Enter Flat");
                    return;
                }
                if(TextUtils.isEmpty(dcity)){
                    doccity.setError("Please Enter a City");
                    return;
                }
                if(TextUtils.isEmpty(dcountry)){
                    doccountry.setError("Please Enter a Country");
                    return;
                }
                if(TextUtils.isEmpty(droad)){
                    docroad.setError("Please Enter a Road");
                    return;
                }
                if(TextUtils.isEmpty(doclocality)){
                    docLocality.setError("Please Enter a Locality");
                    return;
                }
                if(TextUtils.isEmpty(dreg)){
                    docReg.setError("Please Enter a Registration Number");
                    return;
                }
                if(TextUtils.isEmpty(dmedhidtory)){
                    docmedhistory.requestFocus();
                    Toast.makeText(getActivity(), "Please Enter a Medical History", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(dstate)){
                    docstate.requestFocus();
                    Toast.makeText(getActivity(), "Please Enter a State", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(dpincode)){
                    docpincode.requestFocus();
                    Toast.makeText(getActivity(), "Please Enter a Pincode", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(dqual)){
                    docQual.requestFocus();
                    Toast.makeText(getActivity(), "Please Enter Qualification Details", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(dinsti)){
                    docInsti.requestFocus();
                    Toast.makeText(getActivity(), "Please Enter a Institute Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(dcurrInsti)){
                    docWrkngInsti.requestFocus();
                    Toast.makeText(getActivity(), "Please Enter a Working Institute Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(dcertifi)){
                    docCertifi.requestFocus();
                    Toast.makeText(getActivity(), "Please Enter a Certfication Title", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(dspin)){
                    docSpin.requestFocus();
                    Toast.makeText(getActivity(), "Please Select Specialist You are in ", Toast.LENGTH_SHORT).show();
                    return;
                }

                regFormDoctor();
            }
        });
        return v;
    }

    public void regFormDoctor(){
        //final String pfname,final String pmnane, final String pdob, final String pnat, final String plan,
        //                           final String pblod, final String pflat, final String ploc, final String pcity, final String proad,
        //             final String pstate, final String pcount, final String ppin
        progressbar.setVisibility(View.VISIBLE);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DoctorData doctorData = dataSnapshot.getValue(DoctorData.class);
                addToDatabse(firebaseUser.getUid());
                Toast.makeText(context, " Data Inserted ", Toast.LENGTH_SHORT).show();
                Toast.makeText(context, "Form Submitted Successfully :)", Toast.LENGTH_LONG).show();
                showDialog();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(context, " Not submit data ", Toast.LENGTH_SHORT).show();
                onNotSucessDialog();
            }
        });

    }
    @OnClick(R.id.doc_calenderbtn)
    public void calBtn(){
        new SpinnerDatePickerDialogBuilder()
                .context(getActivity())
                .callback(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar calendar = new GregorianCalendar(year, monthOfYear, dayOfMonth);
                        docDoB.setText(simpleDateFormat.format(calendar.getTime()));
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



    public void addToDatabse(String dtrUid){
        DoctorData doctorData = new DoctorData();
        doctorData.setDocFatherName(docFname.getText().toString());
        doctorData.setDocMotherName(docMname.getText().toString());
        doctorData.setDocDob(docDoB.getText().toString());
        doctorData.setDocLanguagespoken(doclangspoken.getText().toString());
        doctorData.setDocBloodgroup(docblood.getText().toString());
        doctorData.setDocFlatNoAndBLDGName(docflat.getText().toString());
        doctorData.setDocAreaLocality(docLocality.getText().toString());
        doctorData.setDocCity(doccity.getText().toString());
        doctorData.setDocCountry(doccountry.getText().toString());
        doctorData.setDocPinCode(docpincode.getText().toString());
        doctorData.setDocRoadNoName(docroad.getText().toString());
        doctorData.setDocNationality(docNation.getText().toString());
        doctorData.setDocPastmedicalhistory(docmedhistory.getText().toString());
        doctorData.setDocQualification(docQual.getText().toString());
        doctorData.setDocState(docstate.getText().toString());
        doctorData.setDocRegistration(docReg.getText().toString());
        doctorData.setDocSpecSpinner(docSpin.getSelectedItem().toString());
        doctorData.setDocInstituteName(docInsti.getText().toString());
        doctorData.setDocPresntInstituteName(docWrkngInsti.getText().toString());
        doctorData.setDocCertfiTitle(docCertifi.getText().toString());
        doctorData.setDocExperience(docExp.getText().toString());
        doctorData.setDocUid(dtrUid);

        myRef.child(firebaseUser.getUid()).setValue(doctorData);


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
