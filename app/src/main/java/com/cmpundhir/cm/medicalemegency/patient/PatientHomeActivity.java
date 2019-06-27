package com.cmpundhir.cm.medicalemegency.patient;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.cmpundhir.cm.bottom_patient_fragments.DashboardFragment;
import com.cmpundhir.cm.bottom_patient_fragments.HomeFragment;
import com.cmpundhir.cm.bottom_patient_fragments.NotificationsFragment;
import com.cmpundhir.cm.bottom_patient_fragments.OnFragmentInteractionListener;
import com.cmpundhir.cm.medical_services.MapActivity;
import com.cmpundhir.cm.medical_services.pdf_view.HealthArticles;
import com.cmpundhir.cm.medicalemegency.LoginChoiceActivity;
import com.cmpundhir.cm.medicalemegency.utils.Prefs;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.cmpundhir.cm.medicalemegency.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class PatientHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener , OnFragmentInteractionListener {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    String currentUserGender;
    CircleImageView headerimage;



    private BottomNavigationView.OnNavigationItemSelectedListener monNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            int id=menuItem.getItemId();
            switch (id){
                case R.id.navigation_home:
                    setFragment(HomeFragment.newInstance("",""));
                    break;
                case R.id.navigation_dashboard:
                    setFragment(DashboardFragment.newInstance("",""));
                    break;
                case R.id.navigation_notifications:
                    setFragment(NotificationsFragment.newInstance("0","1"));
                    break;
            }
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        View headerView=navigationView.getHeaderView(0);
        TextView patName,patEmail;
        headerimage=headerView.findViewById(R.id.imageView1);
        patName = headerView.findViewById(R.id.pat_name);
        patEmail=headerView.findViewById(R.id.pat_email);
        patName.setText(firebaseUser.getDisplayName());
        patEmail.setText(firebaseUser.getEmail());


        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomViewPateint);
        bottomNavigationView.setOnNavigationItemSelectedListener(monNavigationItemSelectedListener);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        Prefs.init(this);
        navigationView.setNavigationItemSelectedListener(this);
        setFragment(HomeFragment.newInstance("",""));
        queryGender();
    }
    public void queryGender(){
        databaseReference.child("patients").child(firebaseUser.getUid())
                .child("userGender").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentUserGender = dataSnapshot.getValue(String.class);
                if(currentUserGender.equals("Male")){
                    headerimage.setImageResource(R.drawable.male);
                }else{
                    headerimage.setImageResource(R.drawable.woman);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

       if (id == R.id.nav_prof) {

        } else if (id == R.id.nav_article) {
            Intent intent = new Intent(PatientHomeActivity.this, HealthArticles.class);
            startActivity(intent);


        } else if (id == R.id.nav_treatmnt) {

        } else if (id == R.id.nav_diagnsis) {




        } else if (id == R.id.nav_pat_logout) {
            Prefs.setAuth(false);
            Prefs.commit();
            Intent intent = new Intent(PatientHomeActivity.this, LoginChoiceActivity.class);
            startActivity(intent);
            finish();
        }else if (id == R.id.services) {

            Intent intent = new Intent(PatientHomeActivity.this, MapActivity.class);
            startActivity(intent);
        }

            DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public  void setFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.containerPatient,fragment);
        fragmentTransaction.commit();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
