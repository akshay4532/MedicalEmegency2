package com.cmpundhir.cm.medical_services;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.cmpundhir.cm.medicalemegency.R;

public class MapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_map);
    }

    public void open1(View view) {
        Uri ref=Uri.parse("geo:0,0?q=HOSPITALS");
        Intent i=new Intent(Intent.ACTION_VIEW,ref);
        i.setPackage("com.google.android.apps.maps");
        startActivity(i);
    }

    public void open2(View view) {
        Uri ref=Uri.parse("geo:0,0?q=PHARMACY");
        Intent i=new Intent(Intent.ACTION_VIEW,ref);
        i.setPackage("com.google.android.apps.maps");
        startActivity(i);

    }

    public void open3(View view) {
        Uri ref=Uri.parse("geo:0,0?q=AMBULANCE");
        Intent i=new Intent(Intent.ACTION_VIEW,ref);
        i.setPackage("com.google.android.apps.maps");
        startActivity(i);
    }
}
