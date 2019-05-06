package com.cmpundhir.cm.medicalemegency.patient;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.cmpundhir.bottom_patient_fragments.DashboardFragment;
import com.cmpundhir.bottom_patient_fragments.HomeFragment;
import com.cmpundhir.bottom_patient_fragments.NotificationsFragment;
import com.cmpundhir.bottom_patient_fragments.OnFragmentInteractionListener;
import com.cmpundhir.cm.medical_services.MapActivity;
import com.cmpundhir.cm.medicalemegency.LoginChoiceActivity;
import com.cmpundhir.cm.medicalemegency.model.User;
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

public class PatientHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener , OnFragmentInteractionListener {

    TextView patName,patEmail;

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
                    setFragment(NotificationsFragment.newInstance("",""));
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
        patName=findViewById(R.id.patientName);

        patEmail=findViewById(R.id.patientEmail);



        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        View headerView=navigationView.getHeaderView(0);


        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomViewPateint);
        bottomNavigationView.setOnNavigationItemSelectedListener(monNavigationItemSelectedListener);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        Prefs.init(this);
        navigationView.setNavigationItemSelectedListener(this);
        setFragment(HomeFragment.newInstance("",""));
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

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.services) {

           Intent intent = new Intent(PatientHomeActivity.this,MapActivity.class);
           startActivity(intent);


        } else if (id == R.id.nav_pat_logout) {
            Prefs.setAuth(false);
            Prefs.commit();
            Intent intent = new Intent(PatientHomeActivity.this, LoginChoiceActivity.class);
            startActivity(intent);
            finish();
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
