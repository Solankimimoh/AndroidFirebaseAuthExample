package com.lj.ljengineeringcollege;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

    //    Component Initlization
    private TextView userNameTv;
    private TextView userEmailTv;
    private RecyclerView recyclerView;
    private ArrayList<HomeMenuItemModel> arrayList;

    //    Firebase Init
    private DatabaseReference DataRef;
    private FirebaseAuth auth;


    //    variable
    private String loginType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        auth = FirebaseAuth.getInstance();
        DataRef = FirebaseDatabase.getInstance().getReference();

        initView();

        final Intent intent = getIntent();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        if (loginType != null && !loginType.isEmpty()) {
            Log.e("TAG_LOGIN", intent.getStringExtra("KEY_LOGIN_TYPE"));
            Toast.makeText(this, "BTN" + intent.getStringExtra("KEY_LOGIN_TYPE"), Toast.LENGTH_SHORT).show();

            if (loginType.equals(AppConstant.FIREBASE_TABLE_STUDNET)) {
                loginType = intent.getStringExtra("KEY_LOGIN_TYPE");
                navigationView.getMenu().clear();
                navigationView.inflateMenu(R.menu.student_menu);
                DataRef.child(loginType)
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                Toast.makeText(HomeActivity.this, "Welcome " + dataSnapshot.child(auth.getCurrentUser().getUid()).child(AppConstant.FIREBASE_TABLE_FULLNAME).getValue().toString(), Toast.LENGTH_SHORT).show();
                                final String userName = dataSnapshot.child(auth.getCurrentUser().getUid()).child(AppConstant.FIREBASE_TABLE_FULLNAME).getValue().toString();
                                final String userEmail = dataSnapshot.child(auth.getCurrentUser().getUid()).child(AppConstant.FIREBASE_TABLE_EMAIL).getValue().toString();
                                userNameTv.setText(userName);
                                userEmailTv.setText(userEmail);
                            }

                            @Override
                            public void onCancelled(DatabaseError error) {
                                // Failed to read value
                                Log.e("TAG", "Failed to read value.", error.toException());
                            }
                        });
            } else if (loginType.equals(AppConstant.FIREBASE_TABLE_FACULTY)) {
                Toast.makeText(this, "Faculty", Toast.LENGTH_SHORT).show();
                loginType = intent.getStringExtra("KEY_LOGIN_TYPE");

                DataRef.child(loginType)
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                Toast.makeText(HomeActivity.this, "Welcome " + dataSnapshot.child(auth.getCurrentUser().getUid()).child(AppConstant.FIREBASE_TABLE_FULLNAME).getValue().toString(), Toast.LENGTH_SHORT).show();
                                final String userName = dataSnapshot.child(auth.getCurrentUser().getUid()).child(AppConstant.FIREBASE_TABLE_FULLNAME).getValue().toString();
                                final String userEmail = dataSnapshot.child(auth.getCurrentUser().getUid()).child(AppConstant.FIREBASE_TABLE_EMAIL).getValue().toString();
                                userNameTv.setText(userName);
                                userEmailTv.setText(userEmail);
                            }

                            @Override
                            public void onCancelled(DatabaseError error) {
                                // Failed to read value
                                Log.e("TAG", "Failed to read value.", error.toException());
                            }
                        });
            }
        } else {
            Toast.makeText(this, "ELSE", Toast.LENGTH_SHORT).show();
            auth.signOut();
        }

        final View headerView = navigationView.getHeaderView(0);

        userNameTv = headerView.findViewById(R.id.nav_header_home_username);
        userEmailTv = headerView.findViewById(R.id.nav_header_home_email);


        arrayList = new ArrayList<>();
        arrayList.add(new HomeMenuItemModel(getString(R.string.department), R.drawable.vector_department, "#09A9FF"));
        arrayList.add(new HomeMenuItemModel(getString(R.string.gallery), R.drawable.vector_gallery, "#3E51B1"));
        arrayList.add(new HomeMenuItemModel(getString(R.string.sbifees), R.drawable.vector_fees, "#673BB7"));
        arrayList.add(new HomeMenuItemModel(getString(R.string.notification), R.drawable.vector_notification, "#4BAA50"));
        arrayList.add(new HomeMenuItemModel(getString(R.string.aboutus), R.drawable.vector_about_us, "#F94336"));
        arrayList.add(new HomeMenuItemModel(getString(R.string.contactus), R.drawable.vector_location, "#0A9B88"));

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, arrayList, this);
        recyclerView.setAdapter(adapter);

        AutoFitGridLayoutManager layoutManager = new AutoFitGridLayoutManager(this, 300);
        recyclerView.setLayoutManager(layoutManager);


    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
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

            case R.id.menu_aprove_student:
                final Intent gotoStudentRequest = new Intent(HomeActivity.this, StudentReuqestListActivity.class);
                startActivity(gotoStudentRequest);
                break;

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemClick(HomeMenuItemModel item) {


        if (item.text.equals(getString(R.string.contactus))) {
            final Intent gotoLocation = new Intent(HomeActivity.this, CollegeLocationActivity.class);
            startActivity(gotoLocation);
        } else if (item.text.equals(getString(R.string.sbifees))) {
            openSBITab();
        } else if (item.text.equals(getString(R.string.department))) {
            final Intent gotoDepartment = new Intent(HomeActivity.this, DepartmentListActivity.class);
            startActivity(gotoDepartment);
        }
    }

    private void openSBITab() {
        String url = "https://www.onlinesbi.com/prelogin/institutiontypedisplay.htm";
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setToolbarColor(getResources().getColor(R.color.colorPrimary));
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(url));
    }


}