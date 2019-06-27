package com.cmpundhir.cm.medical_services.pdf_view;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

import com.cmpundhir.cm.medicalemegency.R;
import com.github.barteksc.pdfviewer.PDFView;

public class a1_healthy_you_healthy_environment_activity extends AppCompatActivity {
    PDFView pdfView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a1_healthy_you_healthy_environment_layout);

        pdfView= findViewById(R.id.a1pdfView);

        pdfView.fromAsset("a1_healthy_you_healthy_environment.pdf").load();



    }


}