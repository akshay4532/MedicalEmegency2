package com.cmpundhir.cm.medicalemegency.doctor;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.cmpundhir.cm.bottom_doctor_fragments.DoctorDashboardFragment;
import com.cmpundhir.cm.bottom_doctor_fragments.DoctorHomeFragment;
import com.cmpundhir.cm.bottom_doctor_fragments.DoctorNotificationsFragment;
import com.cmpundhir.cm.bottom_doctor_fragments.OnFragmentInteractionListener;
import com.cmpundhir.cm.medicalemegency.LoginChoiceActivity;
import com.cmpundhir.cm.medicalemegency.R;
import com.cmpundhir.cm.medicalemegency.utils.Prefs;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.Menu;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;


public class DoctorHome2Activity extends AppCompatActivity
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
                        case R.id.navigation_doc_home:
                            Log.d("DEBUG_DOC","navigation_DEBUG_DOC_home invoked");
                            setFragment(DoctorHomeFragment.newInstance("",""));
                            break;
                        case R.id.navigation_doc_dashboard:
                            Log.d("DEBUG_DOC","navigation_doc_dashboard invoked");
                            setFragment(DoctorDashboardFragment.newInstance("",""));
                            break;
                        case R.id.navigation_doc_notifications:
                            Log.d("DEBUG_DOC","navigation_doc_notifications invoked");
                           setFragment(DoctorNotificationsFragment.newInstance("",""));
                            break;
                    }
                    Log.d("DEBUG_DOC","monNavigationItemSelectedListener invoked");
                    return true;
                }
            };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_home2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        FloatingActionButton fab = findViewById(R.id.fab);
//
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        View headerView = navigationView.getHeaderView(0);
        TextView headerName,headerEmail;
        headerimage=headerView.findViewById(R.id.imageView1);
        headerName = headerView.findViewById(R.id.doc_name);
        headerEmail=headerView.findViewById(R.id.doc_email);
        headerName.setText(firebaseUser.getDisplayName());
        headerEmail.setText(firebaseUser.getEmail());



        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_doc);
        bottomNavigationView.setOnNavigationItemSelectedListener(monNavigationItemSelectedListener);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        setFragment(DoctorHomeFragment.newInstance("",""));
        Log.d("DEBUG_DOC","setFragment invoked");
        queryGender();
    }

    public void queryGender(){
        databaseReference.child("doctors").child(firebaseUser.getUid())
                .child("userGender").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentUserGender = dataSnapshot.getValue(String.class);
                if(currentUserGender.equals("Male")){
                    headerimage.setImageResource(R.drawable.doctor_male);
                }else{
                    headerimage.setImageResource(R.drawable.doctor_female);
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
            System.exit(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.doctor_home2, menu);
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

        if (id == R.id.nav_doc_prof_icon) {
            // Handle the camera action
        }
        else if (id == R.id.nav_doc_pat_details)  {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_doc_logout) {
            Prefs.setAuth(false);
            Prefs.commit();
            Intent intent = new Intent(DoctorHome2Activity.this, LoginChoiceActivity.class);
            startActivity(intent);
            finish();

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.containerDoctor,fragment);
        fragmentTransaction.commit();
        Log.d("DEBUG_DOC","setFragment invoked");
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
