package com.cmpundhir.cm.medicalemegency;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.cmpundhir.cm.medicalemegency.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PatientRegActivity extends AppCompatActivity {

    private static final String TAG = "PateintReg";
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
    Button btn;
    ProgressBar progressbar;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("users");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_reg);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();

        btn = findViewById(R.id.button2);
        progressbar = findViewById(R.id.progressbar);
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
                    genderr = ((RadioButton) findViewById(gender.getCheckedRadioButtonId())).getText().toString();
                }catch (NullPointerException e){
                    Toast.makeText(PatientRegActivity.this, "Please select your gender", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(email)){
                    emailEdit.setError("Please enter your email");
                    return;
                }

                if(TextUtils.isEmpty(genderr)){
                    gender.requestFocus();
                    Toast.makeText(PatientRegActivity.this, "Please select your gender", Toast.LENGTH_SHORT).show();
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
                regPateint(email,pss,name,genderr,phone);
            }
        });
    }

    public void regPateint(final String email,final String password, final String name, final String gender, final String mob){
        progressbar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            verifyEMmail(user);
                            updateDetails(user,fname.getText().toString()+" "+lname.getText().toString());
                            progressbar.setVisibility(View.GONE);
                            addToDatabse(user,name,email,user.getUid(),gender,mob);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(PatientRegActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            progressbar.setVisibility(View.GONE);
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
                            AlertDialog.Builder builder = new AlertDialog.Builder(PatientRegActivity.this);

                        }
                    }
                });
    }

    public void addToDatabse(FirebaseUser firebaseUser,String name,String email,String userId,String gender,String mob){
        User user = new User();
        user.setUserName(name);
        user.setUserEmail(email);
        user.setUserId(userId);
        user.setUserGender(gender);
        user.setUserPhone(mob);
        user.setUserType("1");
        user.setStatus("1");
        myRef.child(firebaseUser.getUid()).setValue(user);
    }
}
