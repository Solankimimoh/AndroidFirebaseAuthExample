package com.lj.ljengineeringcollege;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {


    private TextView nameTv;
    private TextView emailTv;
    private TextView mobileTv;
    private TextView departmentTv;
    private TextView enrollmentTv;

    //    Firebase Init
    private DatabaseReference DataRef;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        auth = FirebaseAuth.getInstance();
        DataRef = FirebaseDatabase.getInstance().getReference();

        initView();

        Intent intent = getIntent();

        final String loginType = intent.getStringExtra("KEY_LOGIN_TYPE");

        DataRef.child(loginType).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                nameTv.setText(dataSnapshot.child(auth.getCurrentUser().getUid()).child(AppConstant.FIREBASE_TABLE_FULLNAME).getValue().toString());
                departmentTv.setText(dataSnapshot.child(auth.getCurrentUser().getUid()).child(AppConstant.FIREBASE_DEPARTMENT).getValue().toString());
                emailTv.setText(dataSnapshot.child(auth.getCurrentUser().getUid()).child(AppConstant.FIREBASE_TABLE_EMAIL).getValue().toString());
                if (loginType.equals(AppConstant.FIREBASE_TABLE_FACULTY)) {
                    enrollmentTv.setVisibility(View.INVISIBLE);
                } else {
                    enrollmentTv.setVisibility(View.VISIBLE);
                    enrollmentTv.setText(dataSnapshot.child(auth.getCurrentUser().getUid()).child(AppConstant.FIREBASE_TABLE_ENROLLMENT).getValue().toString());
                }
                mobileTv.setText(dataSnapshot.child(auth.getCurrentUser().getUid()).child(AppConstant.FIREBASE_TABLE_MOBILE).getValue().toString());
                Log.e("PROFILE", auth.getCurrentUser().getUid() + "");
                Log.e("PROFILE", dataSnapshot.child(auth.getCurrentUser().getUid()).getValue() + "");
                Log.e("PROFILE", DataRef.getParent() + "");
                Log.e("PROFILE", DataRef.getParent() + "");
                Log.e("PROFILE", DataRef.getRoot() + "");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void initView() {
        nameTv = findViewById(R.id.activity_profile_name);
        emailTv = findViewById(R.id.activity_profile_email);
        mobileTv = findViewById(R.id.activity_profile_mobile);
        departmentTv = findViewById(R.id.activity_profile_department);
        enrollmentTv = findViewById(R.id.activity_profile_enrollment);
    }
}
