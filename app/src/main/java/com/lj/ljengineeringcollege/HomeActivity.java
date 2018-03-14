package com.lj.ljengineeringcollege;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, RecyclerViewAdapter.ItemListener {

    private FirebaseAuth auth;
    private TextView userNameTv;
    private TextView userEmailTv;
    private DatabaseReference DataRef;
    private RecyclerView recyclerView;
    private ArrayList<HomeMenuItemModel> arrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        auth = FirebaseAuth.getInstance();
        DataRef = FirebaseDatabase.getInstance().getReference().child(AppConstant.FIREBASE_TABLE_STUDNET);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        final View headerView = navigationView.getHeaderView(0);

        userNameTv = headerView.findViewById(R.id.nav_header_home_username);
        userEmailTv = headerView.findViewById(R.id.nav_header_home_email);

        DataRef.child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                StudentModel user = dataSnapshot.getValue(StudentModel.class);
                if (user != null) {
                    userNameTv.setText(user.getFullName());
                    userEmailTv.setText(user.getEmail());
                } else {
                    auth.signOut();
                    finish();
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        arrayList = new ArrayList<>();
        arrayList.add(new HomeMenuItemModel("Department", R.drawable.vector_aboutus, "#09A9FF"));
        arrayList.add(new HomeMenuItemModel("Gallery", R.drawable.vector_email, "#3E51B1"));
        arrayList.add(new HomeMenuItemModel("SBI FEES", R.drawable.vector_developer, "#673BB7"));
        arrayList.add(new HomeMenuItemModel("Notification", R.drawable.vector_mobile, "#4BAA50"));
        arrayList.add(new HomeMenuItemModel("About Us", R.drawable.vector_user, "#F94336"));
        arrayList.add(new HomeMenuItemModel("Contact Us", R.drawable.vector_location, "#0A9B88"));

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, arrayList, this);
        recyclerView.setAdapter(adapter);

        AutoFitGridLayoutManager layoutManager = new AutoFitGridLayoutManager(this, 500);
        recyclerView.setLayoutManager(layoutManager);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.menu_signout:
                auth.signOut();
                finish();
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemClick(HomeMenuItemModel item) {
        switch (item.text) {

            case "Contact Us":
                Toast.makeText(this, "location", Toast.LENGTH_SHORT).show();
                Intent gotoLocation = new Intent(HomeActivity.this, CollegeLocationActivity.class);
                startActivity(gotoLocation);
                break;
        }
    }
}