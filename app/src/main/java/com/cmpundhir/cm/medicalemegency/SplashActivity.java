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

import com.airbnb.lottie.LottieAnimationView;
import com.cmpundhir.cm.InternetConnection;
import com.cmpundhir.cm.medicalemegency.doctor.DoctorHome2Activity;
import com.cmpundhir.cm.medicalemegency.patient.PatientHomeActivity;
import com.cmpundhir.cm.medicalemegency.utils.Prefs;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {
//    @BindView(R.id.img)
//   // ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        Prefs.init(this);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(Prefs.getAuth()){
                    Intent intent = null;
                    switch (Prefs.getUserType()){
                        case 1:
                            Prefs.setUserType(1);
                            intent = new Intent(SplashActivity.this, PatientHomeActivity.class);
                            break;
                        case 2:
                            Prefs.setUserType(2);
                            intent = new Intent(SplashActivity.this, DoctorHome2Activity.class);
                            break;
                    }
                    startActivity(intent);
                    finish();
                }else {
                   // ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SplashActivity.this, img, "imageTransition");
                    Intent intent = new Intent(SplashActivity.this, LoginChoiceActivity.class);
                   startActivity(intent);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                            overridePendingTransition(0,0);
                        }
                    }, 1000);
                }

            }
        },3000);
    }

}
