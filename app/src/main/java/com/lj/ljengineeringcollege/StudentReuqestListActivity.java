package com.lj.ljengineeringcollege;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentReuqestListActivity extends AppCompatActivity {

    //Componenet Init
    private ListView studentListview;
    private ArrayList<String> studentArrayList;
    private ProgressDialog progressDialog;

    //Firebase
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_reuqest_list);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        initView();

        studentArrayList = new ArrayList<>();
        final ArrayAdapter<String> studentListArrayAdapter = new ArrayAdapter<String>(StudentReuqestListActivity.this, R.layout.row_student_request_layout, R.id.row_layout_category_categoryname_tv, studentArrayList);
        studentListview.setAdapter(studentListArrayAdapter);

        progressDialog.show();
        mDatabase.child(AppConstant.FIREBASE_TABLE_STUDNET).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot categoryList : dataSnapshot.getChildren()) {
                    Log.e("TAG -S", categoryList.getValue().toString());

// for (DataSnapshot categoryName : categoryList.getChildren()) {
//                        Log.e("TAG", categoryName.getValue() + "");
//                        studentArrayList.add(categoryName.getValue().toString());
//                    }
                }
                studentListArrayAdapter.notifyDataSetChanged();
                progressDialog.hide();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void initView() {
        studentListview = findViewById(R.id.activity_view_category_list_lv);

        progressDialog = new ProgressDialog(StudentReuqestListActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("getting students name....");
    }
}
