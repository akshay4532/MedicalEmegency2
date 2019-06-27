package com.cmpundhir.cm.medical_services.pdf_view;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

import com.cmpundhir.cm.medicalemegency.R;
import com.github.barteksc.pdfviewer.PDFView;

public class a5_say_no_to_constipation_activity extends AppCompatActivity {
    PDFView pdfView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a5_say_no_to_constipation_layout);

        pdfView = findViewById(R.id.a5pdfView);

        pdfView.fromAsset("a5_say_no_to_constipation.pdf").load();



    }


}