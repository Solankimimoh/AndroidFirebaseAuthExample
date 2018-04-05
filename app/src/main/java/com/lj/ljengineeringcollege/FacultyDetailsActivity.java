package com.lj.ljengineeringcollege;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FacultyDetailsActivity extends AppCompatActivity implements FacultyDetailsAdapter.ItemListener {

    private RecyclerView facultyDetailsRecyclerView;
    private ArrayList<FacultyDetailsModel> facultyDetailsModelArrayList;
    private FacultyDetailsAdapter facultyDetailsAdapter;
    private Intent intent;
    ProgressDialog progressDialog;

    //    Firebase Init
    private DatabaseReference DataRef;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_details);
        DataRef = FirebaseDatabase.getInstance().getReference("departmentdata");


        initView();
        intent = getIntent();
        if (intent != null) {

            final String departmentName = intent.getStringExtra("KEY_DEPARTMENT").toLowerCase().replace(" ", "");
            Toast.makeText(FacultyDetailsActivity.this, "" + departmentName, Toast.LENGTH_SHORT).show();

            DataRef.child(departmentName).child(AppConstant.FIREBASE_DETAILS_FACULTY).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot facultyModelDataSnapshot : dataSnapshot.getChildren()) {
                        FacultyDetailsModel facultyDetailsModel = facultyModelDataSnapshot.getValue(FacultyDetailsModel.class);
                        Log.e("NAME", facultyDetailsModel.getName());
                        facultyDetailsModelArrayList.add(facultyDetailsModel);
                        facultyDetailsAdapter.notifyDataSetChanged();


                    }
                    progressDialog.dismiss();

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e("ERROR", databaseError.getMessage());
                    Toast.makeText(FacultyDetailsActivity.this, "EROR" + databaseError.getDetails(), Toast.LENGTH_SHORT).show();
                }
            });

            facultyDetailsAdapter = new FacultyDetailsAdapter(FacultyDetailsActivity.this, facultyDetailsModelArrayList, this);
            facultyDetailsRecyclerView.setAdapter(facultyDetailsAdapter);

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            facultyDetailsRecyclerView.setLayoutManager(layoutManager);

        }


    }


    private void initView() {
        facultyDetailsRecyclerView = findViewById(R.id.activity_faculty_details_recycleview);
        progressDialog = new ProgressDialog(FacultyDetailsActivity.this);

        progressDialog.setTitle("Faculty Details");
        progressDialog.setMessage("Loading.....");
        progressDialog.show();
        facultyDetailsModelArrayList = new ArrayList<>();


    }


    @Override
    public void onItemClick(FacultyDetailsModel item) {

    }
}
