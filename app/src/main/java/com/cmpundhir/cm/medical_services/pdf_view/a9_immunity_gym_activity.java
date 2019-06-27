package com.cmpundhir.cm.medical_services.pdf_view;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

import com.cmpundhir.cm.medicalemegency.R;
import com.github.barteksc.pdfviewer.PDFView;

public class a9_immunity_gym_activity extends AppCompatActivity {
    PDFView pdfView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a9_immunity_gym_layout);

        pdfView= findViewById(R.id.a9pdfView);

        pdfView.fromAsset("a9_immunity_gym.pdf").load();



    }


}