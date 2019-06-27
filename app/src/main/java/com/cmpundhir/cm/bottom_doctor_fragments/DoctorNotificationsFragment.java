package com.cmpundhir.cm.bottom_doctor_fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cmpundhir.cm.medicalemegency.R;
import com.cmpundhir.cm.medicalemegency.model.Notify;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DoctorNotificationsFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    OnFragmentInteractionListener mListener;

    public DoctorNotificationsFragment() {
        // Required empty public constructor
    }
    public static DoctorNotificationsFragment newInstance(String param1, String param2) {
        DoctorNotificationsFragment fragment = new DoctorNotificationsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private static final String TAG = "Doctor Noti";
   @BindView(R.id.title)
   EditText tit;
    @BindView(R.id.descript)
    EditText desc;
    @BindView(R.id.btnSendNotiDoc)
            Button sendNoti;

      Context context;
    public FirebaseAuth mAuth;


    ProgressBar progressbar;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef ;
    FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_doctor_notifications, container, false);
        ButterKnife.bind(this, view);
        progressbar = view.findViewById(R.id.progressbar_docNoti);


       sendNoti.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                String ti=tit.getText().toString().trim();
              String des=desc.getText().toString().trim();

              if (TextUtils.isEmpty(ti)) {
                   tit.setError("Please Enter your title");
                   return;
               }

               if (TextUtils.isEmpty(des)) {
                   desc.setError("Please Enter your description");
                   return;
               }
               progressbar.setVisibility(View.VISIBLE);
            notification(ti,des);
           }

       });
       return  view;
    }

    public void notification(String t,String d){

        progressbar.setVisibility(View.VISIBLE);
        Notify notify=new Notify(t,d);
        myRef=database.getReference();
        myRef.child("Doctor_Noti").push().setValue(notify);
//        Toast.makeText(context, "Send Prescription Sucessfully", Toast.LENGTH_LONG).show();
        progressbar.setVisibility(View.GONE);
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


}
