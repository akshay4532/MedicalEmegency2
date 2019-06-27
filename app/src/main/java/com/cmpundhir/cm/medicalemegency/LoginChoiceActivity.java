package com.cmpundhir.cm.medicalemegency;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.cmpundhir.cm.InternetConnection;
import com.cmpundhir.cm.medicalemegency.R;
import com.cmpundhir.cm.medicalemegency.model.User;
import com.cmpundhir.cm.medicalemegency.utils.DatabaseStatus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginChoiceActivity extends AppCompatActivity {
  //  User user;
//    @BindView(R.id.img)
//    ImageView img;
  LottieAnimationView animationView;
    private long doubleTap;
    private Toast doubleTapToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login_choice);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        View view = LayoutInflater.from(LoginChoiceActivity.this).inflate(R.layout.no_internet,null);
        animationView = view.findViewById(R.id.no_net);
        animationView.setAnimation("no_net.json");
        animationView.playAnimation();
        if (!InternetConnection.ConnectedOrNot(LoginChoiceActivity.this)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(LoginChoiceActivity.this);
            builder.setTitle("Oops !!").setMessage("Internet is Not Working ").setView(view).setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            }).create().show();
        }
    }


//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        finish();
//        overridePendingTransition(0, 0);
//    }

    @Override
    public void onBackPressed() {
        if(doubleTap + 3000>System.currentTimeMillis()){
            doubleTapToast.cancel();
            super.onBackPressed();
            return;
        }else{
            doubleTapToast =
                    Toast.makeText(this,"Press Back Again to Exit MedicalEmergency App",Toast.LENGTH_LONG);
            doubleTapToast.show();
        }
        doubleTap = System.currentTimeMillis();
    }

    @OnClick(R.id.patient_log)
    public void loginAsPatient(View view){
       Intent intent=new Intent(this,LoginActivity.class);
       intent.putExtra("type", DatabaseStatus.USER_TYPE_PATIENT);
       startActivity(intent);
    }
    @OnClick(R.id.doctor_log)
    public void loginAsDoctor(View view){
        Intent intent=new Intent(this,LoginActivity.class);
        intent.putExtra("type", DatabaseStatus.USER_TYPE_DOCTOR);
        startActivity(intent);
    }
    @OnClick(R.id.sign_up)
    public void Signup(View view){
        startActivity(new Intent(LoginChoiceActivity.this,RegistrationChoiceActivity.class));
    }

}
