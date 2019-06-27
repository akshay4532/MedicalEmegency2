package com.cmpundhir.cm.medical_services.pdf_view;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.cmpundhir.cm.medicalemegency.R;

import static androidx.core.content.ContextCompat.startActivity;

public class HealthArticles extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.health_articles_main);
        getSupportActionBar().hide();




        Button a1;
        a1 = (Button) findViewById(R.id.a1);
        a1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a1intent = new Intent(HealthArticles.this, a1_healthy_you_healthy_environment_activity.class);
                startActivity(a1intent);

            }


        });




        Button a2;
        a2 = findViewById(R.id.a2);
        a2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a2intent = new Intent(HealthArticles.this, a2_dos_and_donts_during_pregnancy_activity.class);
                startActivity(a2intent);

            }


        });


        Button a3;
        a3 = (Button) findViewById(R.id.a3);
        a3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a3intent = new Intent(HealthArticles.this, a3_vaccination_for_children_activity.class);
                startActivity(a3intent);

            }


        });


        Button a4;
        a4 = (Button) findViewById(R.id.a4);
        a4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a4intent = new Intent(HealthArticles.this, a4_balance_diet_activity.class);
                startActivity(a4intent);

            }


        });


        Button a5;
        a5 = (Button) findViewById(R.id.a5);
        a5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a5intent = new Intent(HealthArticles.this, a5_say_no_to_constipation_activity.class);
                startActivity(a5intent);

            }


        });


        Button a6;
        a6 = (Button) findViewById(R.id.a6);
        a6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a6intent = new Intent(HealthArticles.this, a6_healthy_heart_activity.class);
                startActivity(a6intent);

            }


        });


        Button a7;
        a7 = (Button) findViewById(R.id.a7);
        a7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a7intent = new Intent(HealthArticles.this, a7_yogasana_activity.class);
                startActivity(a7intent);

            }


        });


        Button a8;
        a8 = (Button) findViewById(R.id.a8);
        a8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a8intent = new Intent(HealthArticles.this, a8_brain_power_is_main_power_activity.class);
                startActivity(a8intent);

            }


        });


        Button a9;
        a9 = (Button) findViewById(R.id.a9);
        a9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a9intent = new Intent(HealthArticles.this, a9_immunity_gym_activity.class);
                startActivity(a9intent);

            }


        });

        Button a10;
        a10 = (Button) findViewById(R.id.a10);
        a10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a10intent = new Intent(HealthArticles.this, a10_home_remedies_activity.class);
                startActivity(a10intent);

            }


        });








    }
}


