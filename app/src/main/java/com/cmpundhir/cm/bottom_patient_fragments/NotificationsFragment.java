package com.cmpundhir.cm.bottom_patient_fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cmpundhir.cm.medical_services.BookAnApointmentActivity;
import com.cmpundhir.cm.medicalemegency.R;
import com.cmpundhir.cm.medicalemegency.model.Notify;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class NotificationsFragment extends Fragment {


    FirebaseDatabase database;
    DatabaseReference myRef;
    FirebaseUser firebaseUser;
    MyAdapter adapter;
   RecyclerView recyclerView;
    final ArrayList<Notify> userlist = new ArrayList<>();

    public static Fragment newInstance(String s, String s1) {
        NotificationsFragment fragment = new NotificationsFragment();
        return fragment;
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public NotificationsFragment() {
        // Required empty public constructor
    }


//    }

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
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        recyclerView = view.findViewById(R.id.recycerpatNotification);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new MyAdapter();

        database = FirebaseDatabase.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        myRef = database.getReference();

        myRef.child("Doctor_Noti").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dttSnapshot2 : dataSnapshot.getChildren()) {

                    Log.d("check_data", dttSnapshot2.getValue().toString());
                    Notify message = dttSnapshot2.getValue(Notify.class);
                    Log.d("data", message.toString());
                    userlist.add(message);
                }
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return view;
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = getLayoutInflater().inflate(R.layout.pat_show_notification_recydesign, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {


            final Notify data = userlist.get(i);


            Log.d("NotificationData", data.toString());
            viewHolder.title.setText(data.getTitle());
            viewHolder.description.setText(data.getDescription());
        }

        @Override
        public int getItemCount() {
            return userlist.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView title, description;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.textViewTitile);
                description = itemView.findViewById(R.id.textViewDescription);
            }
        }

        public void onButtonPressed(Uri uri) {
            if (mListener != null) {
                mListener.onFragmentInteraction(uri);
            }
        }



    }
}