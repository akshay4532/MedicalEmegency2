package com.cmpundhir.cm.bottom_patient_fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cmpundhir.cm.medical_services.BookAnApointmentActivity;
import com.cmpundhir.cm.medicalemegency.R;


public class DashboardFragment extends Fragment {

    int[] imgiIds = {R.drawable.pediatrician_icon,R.drawable.diabetic,R.drawable.ayur,R.drawable.cardio,
            R.drawable.dental,R.drawable.dermatologist,R.drawable.diet,
            R.drawable.ent,R.drawable.gas,R.drawable.homeo,R.drawable.neuro,R.drawable.opthl,R.drawable.ortho
            ,R.drawable.physi,R.drawable.physio,R.drawable.preg,R.drawable.urolo,R.drawable.veterinary_icon};

    String[] texts = {"Paediatrics","Diabetology","Ayurvada","Cardiology","Dental Surgery"
            ,"Dermatologist","Dietitian","ENT","Gastroenterology","Homopathy","Neurology","Ophthlmology"
           ,"Orthopedics" ,"Physician","Physiotherapy","Obstetrics and Gynaecology","Gastroenterology","Veterinary Medicine"};
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public DashboardFragment() {
        // Required empty public constructor
    }

    public static DashboardFragment newInstance(String param1, String param2) {
        DashboardFragment fragment = new DashboardFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        GridView gridView = (GridView) view.findViewById(R.id.grid_view);

        // Instance of ImageAdapter Class
        gridView.setAdapter(new ImageAdapter());
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), BookAnApointmentActivity.class);
                intent.putExtra("doc_type",texts[position]);
                startActivity(intent);
            }
        });
        return view;
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


    public class ImageAdapter extends BaseAdapter {
        private Context context;

        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View gridView;

            if (convertView == null) {
                gridView = inflater.inflate(R.layout.pat_appoinmt_design,parent,false);
                ImageView imageView = gridView.findViewById(R.id.img);
                TextView textView = gridView.findViewById(R.id.txt);
                textView.setText(texts[position]);
                imageView.setImageDrawable(getActivity().getResources().getDrawable(imgiIds[position]));
            } else {
                gridView = (View) convertView;
            }

            return gridView;
        }

        @Override
        public int getCount() {
            return 9;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

    }
}
