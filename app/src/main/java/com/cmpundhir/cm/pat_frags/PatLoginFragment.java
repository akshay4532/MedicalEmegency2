package com.cmpundhir.cm.pat_frags;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cmpundhir.cm.OnFragmentInteractionListener;
import com.cmpundhir.cm.medicalemegency.LoginActivity;
import com.cmpundhir.cm.medicalemegency.R;



public class PatLoginFragment extends Fragment {
    OnFragmentInteractionListener mListener;

    Button cantLog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_pat_login, container, false);
        cantLog=view.findViewById(R.id.patFrag_contLog_btn);
        cantLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cantLog.setVisibility(View.VISIBLE);
                startActivity(new Intent(getActivity(), LoginActivity.class));
                v.onFinishTemporaryDetach();
            }
        });

        return view;
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
