package com.cmpundhir.cm.docReg_frags;

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
import com.cmpundhir.cm.medicalemegency.DoctorRegActivity;
import com.cmpundhir.cm.medicalemegency.LoginChoiceActivity;
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

import java.util.concurrent.Executor;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class DocRegFragment extends Fragment {


    private static final String TAG = "DoctorRegActivity";
    Context context;
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
    @BindView(R.id.mob)
    TextInputEditText mob;
    private FirebaseAuth mAuth;
    @BindView(R.id.reg_btn)
    Button btn;
    ProgressBar progressbar;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Doctors Registration Form Data");
    RelativeLayout relativeLayout;
    OnFragmentInteractionListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         final  View v = inflater.inflate(R.layout.fragment_doc_reg, container, false);
        ButterKnife.bind(this,v);
        mAuth = FirebaseAuth.getInstance();
        relativeLayout=v.findViewById(R.id.rel_layout);
        progressbar = v.findViewById(R.id.progressbar_doc);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name,email,pss,phone,genderr;
                name = fname.getText().toString()+" "+lname.getText().toString();
                email = emailEdit.getText().toString().trim();
                pss = pass.getText().toString();
                phone = mob.getText().toString();

                if(TextUtils.isEmpty(fname.getText().toString())){
                    fname.setError("Please enter your name");
                    return;
                }
                try {
                    genderr = ((RadioButton) v.findViewById(gender.getCheckedRadioButtonId())).getText().toString();
                }catch (NullPointerException e){
                    Toast.makeText(getActivity()," Please select your gender", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(email)){
                    emailEdit.setError("Please Enter your email");
                    return;
                }
                if(!isValidEmail(email)){
                    emailEdit.setError("Please Enter valid email id");
                    return;
                }

                if(TextUtils.isEmpty(genderr)){
                    gender.requestFocus();
                    Toast.makeText(getActivity(), "Please select your gender", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(phone)){
                    mob.setError("Please enter mobile number");
                    return;
                }
                if(TextUtils.isEmpty(pss)){
                    pass.setError("Please enter a strong password");
                    return;
                }
                regDoc(email,pss,name,genderr,phone);
            }
        });
        return v;
    }

        public void regDoc(final String email,final String password, final String name, final String gender, final String mob){
            progressbar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                verifyEmail(user);
                                updateDetails(user,fname.getText().toString()+" "+lname.getText().toString());
                                progressbar.setVisibility(View.GONE);
                                addToDatabse(user,name,email,user.getUid(),gender,mob,password);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Snackbar.make(relativeLayout,task.getException().getMessage(),Snackbar.LENGTH_LONG).setAction("ok",null).show();
                                progressbar.setVisibility(View.GONE);
                                onNotSucessDialog();
                            }




                        }
                    });
        }

    public void updateDetails(FirebaseUser user , String name){
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

    public void verifyEmail(FirebaseUser user){
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
        final Dialog dialog = new Dialog(context,android.R.style.Theme_DeviceDefault_Dialog_Alert);
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
        final  Dialog dialog = new Dialog(context,android.R.style.Theme_Material_Light_Dialog_Alert);
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
        user.setUserId(userId);
        user.setUserGender(gender);
        user.setUserPass(password);
        user.setUserPhone(mob);
        user.setUserType(DatabaseStatus.USER_TYPE_DOCTOR+"");
        user.setStatus(DatabaseStatus.ACTIVE_STATUS+"");
        myRef.child(firebaseUser.getUid()).setValue(user);
       // startActivity(new Intent(DoctorRegActivity.this, LoginChoiceActivity.class));
    }
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            this.context = context;
            mListener = (OnFragmentInteractionListener) context;
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
