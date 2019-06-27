package com.cmpundhir.cm.medical_services.pdf_view;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

import com.cmpundhir.cm.medicalemegency.R;
import com.github.barteksc.pdfviewer.PDFView;

public class a6_healthy_heart_activity extends AppCompatActivity {
    PDFView pdfView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a6_healthy_heart_layout);

        pdfView= findViewById(R.id.a6pdfView);

        pdfView.fromAsset("a6_healthy_heart.pdf").load();



    }


}