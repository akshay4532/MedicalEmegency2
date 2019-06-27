package com.cmpundhir.cm.medicalemegency;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.cmpundhir.cm.OnFragmentInteractionListener;
import com.cmpundhir.cm.docReg_frags.DocFormFragment;
import com.cmpundhir.cm.docReg_frags.DocLoginFragment;
import com.cmpundhir.cm.docReg_frags.DocRegFragment;
import com.cmpundhir.cm.medicalemegency.doctor.DoctorDetailsFormActivity;
import com.cmpundhir.cm.medicalemegency.model.User;
import com.cmpundhir.cm.medicalemegency.utils.DatabaseStatus;
import com.cmpundhir.cm.pat_frags.PatFormFragment;
import com.cmpundhir.cm.pat_frags.PatLoginFragment;
import com.cmpundhir.cm.pat_frags.PatRegFragment;
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
import com.kofigyan.stateprogressbar.StateProgressBar;
import com.kofigyan.stateprogressbar.components.StateItem;
import com.kofigyan.stateprogressbar.listeners.OnStateItemClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DoctorRegActivity extends AppCompatActivity implements OnFragmentInteractionListener {
    String[]data = {"Registration", "Fill Form Details", " Login"};
    StateProgressBar stateProgressBar;
    //    private static final String TAG = "DoctorRegActivity";
//    @BindView(R.id.fname)
//    TextInputEditText fname;
//    @BindView(R.id.lname)
//    TextInputEditText lname;
//    @BindView(R.id.email)
//    TextInputEditText emailEdit;
//    @BindView(R.id.pass)
//    TextInputEditText pass;
//    @BindView(R.id.gender)
//    RadioGroup gender;
//    @BindView(R.id.mob)
//    TextInputEditText mob;
//    private FirebaseAuth mAuth;
//    @BindView(R.id.reg_btn)
//    Button btn;
//    ProgressBar progressbar;
//    FirebaseDatabase database = FirebaseDatabase.getInstance();
//    DatabaseReference myRef = database.getReference("doctors");
//    RelativeLayout relativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_doctor_reg);
        getSupportActionBar().hide();
        stateProgressBar = findViewById(R.id.state);
        stateProgressBar.setStateDescriptionData(data);
        //for fonts..
        stateProgressBar.setStateDescriptionTypeface("");
        stateProgressBar.setStateNumberTypeface("fonts/Questrial-Regular.ttf");
        stateProgressBar.setStateSize(40f);
        stateProgressBar.setStateNumberTextSize(15f);
        stateProgressBar.setStateLineThickness(8f);

        stateProgressBar.enableAnimationToCurrentState(true);

        stateProgressBar.setDescriptionTopSpaceIncrementer(10f);
        stateProgressBar.setStateDescriptionSize(10f);

        //for on click states..
        stateProgressBar.setOnStateItemClickListener(new OnStateItemClickListener() {
            @Override
            public void onStateItemClick(StateProgressBar stateProgressBar, StateItem stateItem, int stateNumber, boolean isCurrentState) {
                Toast.makeText(getApplicationContext(), "You Select :  " + stateItem, Toast.LENGTH_LONG).show();

            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.container,new DocRegFragment()).commit();





//        mAuth = FirebaseAuth.getInstance();
//        relativeLayout=findViewById(R.id.rel_layout);
//
//
//        progressbar = findViewById(R.id.progressbar_doc);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String name,email,pss,phone,genderr;
//                name = fname.getText().toString()+" "+lname.getText().toString();
//                email = emailEdit.getText().toString().trim();
//                pss = pass.getText().toString();
//                phone = mob.getText().toString();
//
//                if(TextUtils.isEmpty(fname.getText().toString())){
//                    fname.setError("Please enter your name");
//                    return;
//                }
//                try {
//                    genderr = ((RadioButton) findViewById(gender.getCheckedRadioButtonId())).getText().toString();
//                }catch (NullPointerException e){
//                    Toast.makeText(DoctorRegActivity.this," select your gender", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if(TextUtils.isEmpty(email)){
//                    emailEdit.setError("Please enter your email");
//                    return;
//                }
//                if(!isValidEmail(email)){
//                    emailEdit.setError("Please enter valid email id");
//                    return;
//                }
//
//                if(TextUtils.isEmpty(genderr)){
//                    gender.requestFocus();
//                    Toast.makeText(DoctorRegActivity.this, "Please select your gender", Toast.LENGTH_SHORT).show();
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
//               regPateint(email,pss,name,genderr,phone);
//            }
//        });
//    }
//
//    public void regPateint(final String email,final String password, final String name, final String gender, final String mob){
//        progressbar.setVisibility(View.VISIBLE);
//        mAuth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "createUserWithEmail:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            verifyEmail(user);
//                            updateDetails(user,fname.getText().toString()+" "+lname.getText().toString());
//                            progressbar.setVisibility(View.GONE);
//                            addToDatabse(user,name,email,user.getUid(),gender,mob,password);
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
//                            Toast.makeText(DoctorRegActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                            Snackbar.make(relativeLayout,task.getException().getMessage(),Snackbar.LENGTH_LONG).setAction("ok",null).show();
//                            progressbar.setVisibility(View.GONE);
//                        }
//
//
//
//
//                    }
//                });
//    }
//
//    public void updateDetails(FirebaseUser user,String name){
//        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
//                .setDisplayName(name)
//                .build();
//
//        user.updateProfile(profileUpdates)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            Log.d(TAG, "User profile updated.");
//                        }
//                    }
//                });
//    }
//
//    public void verifyEmail(FirebaseUser user){
//        user.sendEmailVerification()
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            Log.d(TAG, "Email sent.");
//                            AlertDialog.Builder builder = new AlertDialog.Builder(DoctorRegActivity.this);
//
//                        }
//                    }
//                });
//    }
//
//    public void addToDatabse(FirebaseUser firebaseUser,String name,String email,String userId,String gender,String mob,String password){
//        User user = new User();
//        user.setUserName(name);
//        user.setUserEmail(email);
//        user.setUserId(userId);
//        user.setUserGender(gender);
//        user.setUserPass(password);
//        user.setUserPhone(mob);
//        user.setUserType(DatabaseStatus.USER_TYPE_DOCTOR+"");
//        user.setStatus(DatabaseStatus.ACTIVE_STATUS+"");
//        myRef.child(firebaseUser.getUid()).setValue(user);
//        startActivity(new Intent(DoctorRegActivity.this,LoginChoiceActivity.class));
//    }
//    public static boolean isValidEmail(CharSequence target) {
//        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
//    }
    }
    @Override
    public void onFragmentInteraction(View view) {
        Log.d("Clicked","button cliked");
        switch (view.getId()){
//            case R.id.patReg_btn :
//                Log.d("Clicked","patReg_btn button cliked");
//                //stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
//                getSupportFragmentManager().beginTransaction().replace(R.id.container,new PatFormFragment()).commit();
//                break;
            //  getSupportFragmentManager().addOnBackStackChangedListener(null);
            case R.id.refresh:
                stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);
                getSupportFragmentManager().beginTransaction().replace(R.id.container,new DocRegFragment()).commit();
                break;
            case R.id.k:
                stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
                getSupportFragmentManager().beginTransaction().replace(R.id.container,new DocFormFragment()).commit();
                break;
            case R.id.ok_formPat :
                // Log.d("Clicked","patReg_btn button cliked");
                stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
                getSupportFragmentManager().beginTransaction().replace(R.id.container,new DocLoginFragment()).commit();
                break;
            case R.id.refreshForm:
                stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
                getSupportFragmentManager().beginTransaction().replace(R.id.container,new DocFormFragment()).commit();
                break;
//            case R.id.docFrag_contLog_btn:
//                stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
//                getSupportFragmentManager().beginTransaction().replace(R.id.container,new DocFormFragment()).commit();
//                break;
        }

    }
}
