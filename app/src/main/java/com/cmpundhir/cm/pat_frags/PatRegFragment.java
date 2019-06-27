package com.cmpundhir.cm.pat_frags;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.cmpundhir.cm.OnFragmentInteractionListener;
import com.cmpundhir.cm.medicalemegency.LoginChoiceActivity;
import com.cmpundhir.cm.medicalemegency.PatientRegActivity;
import com.cmpundhir.cm.medicalemegency.R;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;



public class PatRegFragment extends Fragment {
    private static final String TAG = "PateintReg";
    Context context;
    @BindView(R.id.regPatfname)
    TextInputEditText fname;
    @BindView(R.id.lname)
    TextInputEditText lname;
    @BindView(R.id.email)
    TextInputEditText emailEdit;
    @BindView(R.id.pass)
    TextInputEditText pass;
    @BindView(R.id.gender)
    RadioGroup gender;
    @BindView(R.id.mob)
    TextInputEditText mob;
    private FirebaseAuth mAuth;
    @BindView(R.id.rel_layout_patient)
    RelativeLayout relativeLayout;
    OnFragmentInteractionListener mListener;

    Button regBtn;
    ProgressBar progressbar;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Patients Registration Form Data");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_pat_reg, container, false);
        ButterKnife.bind(this, view);
        mAuth = FirebaseAuth.getInstance();
        regBtn = view.findViewById(R.id.patReg_btn);
        progressbar = view.findViewById(R.id.progressbarPati);
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name, lastn, email, pss, phone, genderr;
                name = fname.getText().toString() + " " + lname.getText().toString();
                email = emailEdit.getText().toString().trim();
                pss = pass.getText().toString();
                phone = mob.getText().toString();

                if (TextUtils.isEmpty(fname.getText().toString())) {
                    fname.setError("Please enter your name");
                    return;
                }
                try {
                    genderr = ((RadioButton) view.findViewById(gender.getCheckedRadioButtonId())).getText().toString();
                } catch (NullPointerException e) {
                    Toast.makeText(getActivity(), "Please select your gender", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    emailEdit.setError("Please enter your email");
                    return;
                }
                if (!isValidEmail(email)) {
                    emailEdit.setError("Please enter valid email id");
                    return;
                }

                if (TextUtils.isEmpty(genderr)) {
                    gender.requestFocus();
                    Toast.makeText(getActivity(), "Please select your gender", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(phone)) {
                    mob.setError("Please enter mobile number");
                    return;
                }
                if (TextUtils.isEmpty(pss)) {
                    pass.setError("Please enter a strong password");
                    return;
                }
                regPateint(email, pss, name, genderr, phone);
              //  mListener.onFragmentInteraction(v);
            }
        });
        return view;
    }

    public void regPateint(final String email,final String password, final String name, final String gender, final String mob){
        progressbar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            verifyEMmail(user);
                            updateDetails(user,fname.getText().toString()+" "+lname.getText().toString());
                            progressbar.setVisibility(View.GONE);
                            addToDatabse(user,name,email,user.getUid(),gender,mob,password);
                            Toast.makeText(context, "Registration Sucessful", Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Snackbar.make(relativeLayout,task.getException().getMessage(),Snackbar.LENGTH_LONG).setAction("ok",null).show();
                            progressbar.setVisibility(View.GONE);
                            onNotSucessDialog();
                        }

                        // ...
                    }
                });
    }

    public void updateDetails(FirebaseUser user,String name){
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User profile updated.");
                        }
                    }
                });
    }

    public void verifyEMmail(FirebaseUser user){
        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent.");
                            showDialog();

                        }

                    }
                });
    }

    public void showDialog(){
        final  Dialog dialog = new Dialog(context,android.R.style.Theme_DeviceDefault_Dialog_Alert);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alertdialog);
        dialog.setCancelable(false);
        dialog.show();
        Button ok,kaata;
        ok=dialog.findViewById(R.id.k);
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
        dialog.setContentView(R.layout.alertdialog_notsucess);
        dialog.setCancelable(false);
        dialog.show();
        Button kaata ;
       ImageView ref;
     ref=dialog.findViewById(R.id.refresh);
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
    public void addToDatabse(FirebaseUser firebaseUser,String name,String email,String userId,String gender,String mob,String password){
        User user = new User();
        user.setUserName(name);
        user.setUserEmail(email);
        user.setUserPass(password);
        user.setUserId(userId);
        user.setUserGender(gender);
        user.setUserPhone(mob);
        user.setUserType(DatabaseStatus.USER_TYPE_PATIENT+"");
        user.setStatus(DatabaseStatus.ACTIVE_STATUS+"");
        myRef.child(firebaseUser.getUid()).setValue(user);
//        startActivity(new Intent(getActivity(), LoginChoiceActivity.class));

    }
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }




    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
            this.context = context;
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
