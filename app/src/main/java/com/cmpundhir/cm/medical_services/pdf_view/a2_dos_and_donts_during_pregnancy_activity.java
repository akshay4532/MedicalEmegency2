package com.cmpundhir.cm.medical_services.pdf_view;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

import com.cmpundhir.cm.medicalemegency.R;
import com.github.barteksc.pdfviewer.PDFView;

public class a2_dos_and_donts_during_pregnancy_activity extends AppCompatActivity {
    PDFView pdfView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a2_dos_and_donts_during_pregnancy_layout);

        pdfView= findViewById(R.id.a2pdfView);

        pdfView.fromAsset("a2_dos_and_donts_during_pregnancy.pdf").load();



    }


}