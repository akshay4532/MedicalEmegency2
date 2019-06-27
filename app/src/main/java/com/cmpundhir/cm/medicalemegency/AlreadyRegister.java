package com.cmpundhir.cm.medicalemegency;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.cmpundhir.cm.ForgotPassword;
import com.cmpundhir.cm.medicalemegency.model.User;
import com.cmpundhir.cm.pat_frags.PatFormFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.ProviderQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AlreadyRegister extends AppCompatActivity {
    @BindView(R.id.emailAlrdyReg)
    TextInputEditText mail;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    String currentPatMail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_already_register);
        getSupportActionBar().hide();
        ButterKnife.bind(this);

    }
    @OnClick(R.id.AlrdyReg_btn)
    public void alrdyReg(){
        String email;
        email=mail.getText().toString();

        if(TextUtils.isEmpty(email)){
           mail.setError("Please Enter your Email");
            return;
        } if(!isValidEmail(email)){
            mail.setError("Please Enter valid email id");
            return;
        }else{
            firebaseAuth.fetchProvidersForEmail(mail.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<ProviderQueryResult>() {
                @Override
                public void onComplete(@NonNull Task<ProviderQueryResult> task) {

                    boolean check =!task.getResult().getProviders().isEmpty();
                    if(!check){
                        Toast.makeText(AlreadyRegister.this, "Email Not Found !!", Toast.LENGTH_SHORT).show();
                        Toast.makeText(AlreadyRegister.this, "Please Register yourself First", Toast.LENGTH_SHORT).show();
                    }else{
                        checkMail();
                        Toast.makeText(AlreadyRegister.this, "Email is Found ", Toast.LENGTH_SHORT).show();
                    }

                }
            });


        }
    }

    public void checkMail(){
        final User user = new User();
       databaseReference.child("Patients Registration Form Data").child(firebaseUser.getUid())
               .child("userEmail").addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               currentPatMail=dataSnapshot.getValue(String.class);
               if(currentPatMail.equals(user.getUserEmail())){
                   Intent intent=new Intent(AlreadyRegister.this, PatFormFragment.class);
                   startActivity(intent);
               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });
    }
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());

    }

}
