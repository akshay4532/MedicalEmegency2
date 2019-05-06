package com.cmpundhir.cm.medicalemegency;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.cmpundhir.cm.medicalemegency.doctor.DoctorHome2Activity;
import com.cmpundhir.cm.medicalemegency.model.User;
import com.cmpundhir.cm.medicalemegency.patient.PatientHomeActivity;
import com.cmpundhir.cm.medicalemegency.utils.DatabaseStatus;
import com.cmpundhir.cm.medicalemegency.utils.Prefs;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "Login";
    private FirebaseAuth mAuth;
    @BindView(R.id.email)
    TextInputEditText emailEdit;
    @BindView(R.id.pass)
    TextInputEditText passwordEdit;
    int type;
    RelativeLayout relativeLayout;
    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        Prefs.init(this);

        type = getIntent().getIntExtra("type",0);
        mAuth = FirebaseAuth.getInstance();
    }

    @OnClick(R.id.button)
    public void onLogin(View view){
        String email,pass;
        email=emailEdit.getText().toString().trim();
        pass=passwordEdit.getText().toString().trim();
        if(TextUtils.isEmpty(email)){
            emailEdit.setError("Please Enter Email");
            return;
        }
        if(TextUtils.isEmpty(pass)){
            passwordEdit.setError("Please Enter Password");
            return;
        }
//        if(email==Prefs.getEmail()| pass==Prefs.getPassword()){
//
//                   }
//        else{
//            Snackbar.make(relativeLayout,"You Entered Wrong Password ",Snackbar.LENGTH_LONG).setAction("ok",null).show();
//        }

        signIn(email,pass);

    }

    public void signIn(String email,String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            fetchUserDetails();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.\n"+task.getException().getMessage(),
                                    Toast.LENGTH_LONG).show();

                        }

                        // ...
                    }
                });
    }

    private void fetchUserDetails(){
        String userType ="";

        switch (type){
            case 1:userType = DatabaseStatus.FIREBASE_PATIENTS_REF;
                break;
            case 2:userType = DatabaseStatus.FIREBASE_DOCTOR_REF;
                break;
        }
        FirebaseUser user = mAuth.getCurrentUser();
        mRef.child(userType).child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                User myuser = dataSnapshot.getValue(User.class);
                Prefs.setUserName(myuser.getUserName());
                //Prefs.setUser_L_Name(myuser.);
                Prefs.setEmail(myuser.getUserEmail());
                Prefs.setAuth(true);
                Intent intent = null;
                switch (type){
                    case 1:
                        Prefs.setUserType(1);
                        intent = new Intent(LoginActivity.this, PatientHomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        break;
                    case 2:
                        Prefs.setUserType(2);
                        intent = new Intent(LoginActivity.this, DoctorHome2Activity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        break;
                }
                Prefs.commit();

                Toast.makeText(LoginActivity.this, Prefs.getUserName(), Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(LoginActivity.this, "nhi hua..", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
