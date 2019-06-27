package com.cmpundhir.cm.medical_services;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cmpundhir.cm.medicalemegency.R;
import com.cmpundhir.cm.medicalemegency.model.DoctorData;
import com.cmpundhir.cm.medicalemegency.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BookAnApointmentActivity extends AppCompatActivity {

    Spinner spinner;
    RecyclerView recyclerView;
    MyAdapter adapter1;
    String docType;
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef=database.getReference();
    List<DocQueryData> doctorDataList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_an_apointment);
        spinner = findViewById(R.id.spin);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        docType = getIntent().getStringExtra("doc_type");
        Toast.makeText(this, docType, Toast.LENGTH_SHORT).show();
        String[] arr = getResources().getStringArray(R.array.Category);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,arr);
        spinner.setAdapter(adapter);
        Query query = myRef.child("Doctors_Detail_Form").orderByChild("docSpecSpinner").equalTo(docType);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null) {
                    Log.d("query_data", dataSnapshot.getValue().toString());
                    for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        final DoctorData data = dataSnapshot1.getValue(DoctorData.class);
                        Query query1 = myRef.child("doctors").orderByChild("userId").equalTo(data.getDocUid());
                        query1.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.getValue()!=null) {
                                    Log.d("doc_personal_details", dataSnapshot.getValue().toString());
                                    for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                        User user = dataSnapshot1.getValue(User.class);
                                        DocQueryData queryData = new DocQueryData();
                                        queryData.setDoctorData(data);
                                        queryData.setUser(user);
                                        doctorDataList.add(queryData);
                                        recyclerView.getAdapter().notifyItemChanged(doctorDataList.size());
                                        Toast.makeText(BookAnApointmentActivity.this, "dataset changed", Toast.LENGTH_SHORT).show();
                                        //recyclerView.scrollToPosition(doctorDataList.size()-1);
                                    }

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                    adapter1 = new MyAdapter();
                    recyclerView.setAdapter(adapter1);

                }else{
                    Log.d("query_data", "no doctor found");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        recyclerView = findViewById(R.id.recyclerview);


    }
    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.book_doc_item,parent,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            final DocQueryData data = doctorDataList.get(position);
            holder.title.setText(data.getUser().getUserName());
            holder.bookBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(BookAnApointmentActivity.this,BookingDetailsActivity.class);
                    intent.putExtra("doc_id",data.getUser().getUserId());
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return doctorDataList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView title;
            Button bookBtn;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.title);
                bookBtn = itemView.findViewById(R.id.bookBtn);
            }
        }
    }

    class DocQueryData{
        private DoctorData doctorData;
        private User user;

        public DoctorData getDoctorData() {
            return doctorData;
        }

        public void setDoctorData(DoctorData doctorData) {
            this.doctorData = doctorData;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }
    }
}
