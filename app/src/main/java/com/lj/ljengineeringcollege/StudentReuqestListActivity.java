package com.lj.ljengineeringcollege;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentReuqestListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    //Componenet Init
    private ListView studentListview;
    private ArrayList<String> studentArrayList;
    private ProgressDialog progressDialog;
    private RelativeLayout relativeLayout;
    private Dialog studentDetailsDialog;


    private ArrayAdapter<String> studentListArrayAdapter;


    private String departmentName = null;

    //Firebase
    private DatabaseReference mDatabase;
    private FirebaseAuth firebaseAuth;


    //    String Variable for Dailog
    private String studentName;
    private String studentEmail;
    private String studentEnrollment;
    private String studentMobile;
    private String studentPushKey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_reuqest_list);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        initView();

        getFacultyDepartmentName();

        studentArrayList = new ArrayList<>();
        studentListArrayAdapter = new ArrayAdapter<String>(StudentReuqestListActivity.this, R.layout.row_student_request_layout, R.id.row_layout_category_categoryname_tv, studentArrayList);
        studentListview.setAdapter(studentListArrayAdapter);

        progressDialog.show();
        mDatabase.child(AppConstant.FIREBASE_TABLE_STUDNET).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot categoryList : dataSnapshot.getChildren()) {

                    Log.e("TAG - CAT", categoryList.child(AppConstant.FIREBASE_DEPARTMENT).getValue().toString() + "===" + categoryList.child(AppConstant.FIREBASE_DB_ISACTIVATED).getValue());
                    Log.e("TAG - EMAIL", categoryList.child(AppConstant.FIREBASE_TABLE_EMAIL).getValue().toString());


                    if (categoryList.child(AppConstant.FIREBASE_DEPARTMENT).getValue().equals(departmentName)) {
                        studentListview.setVisibility(View.VISIBLE);
                        relativeLayout.setBackground(null);

                        final boolean isActivated = (boolean) categoryList.child(AppConstant.FIREBASE_DB_ISACTIVATED).getValue();

                        Log.e("TAG _ACTIVATED", isActivated + "");
                        if (!isActivated) {
                            studentArrayList.add(categoryList.child(AppConstant.FIREBASE_TABLE_EMAIL).getValue().toString());
                        }

                    } else {
                        studentListview.setVisibility(View.INVISIBLE);
                        relativeLayout.setBackground(getResources().getDrawable(R.drawable.data_not_found));
                    }

                }
                studentListArrayAdapter.notifyDataSetChanged();
                progressDialog.hide();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void getFacultyDepartmentName() {

        mDatabase.child(AppConstant.FIREBASE_TABLE_FACULTY).child(firebaseAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                departmentName = dataSnapshot.child(AppConstant.FIREBASE_DEPARTMENT).getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void initView() {
        studentListview = findViewById(R.id.activity_view_category_list_lv);
        relativeLayout = findViewById(R.id.student_request_list_layout);

        progressDialog = new ProgressDialog(StudentReuqestListActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("getting students name....");

        studentListview.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        studentDetailsDialog = new AppCompatDialog(StudentReuqestListActivity.this, R.style.Theme_AppCompat_Light_Dialog_MinWidth);
        studentDetailsDialog.setContentView(R.layout.dialog_layout);
        studentDetailsDialog.setTitle("Student Details");

        final String email = parent.getItemAtPosition(position).toString();


        final TextView studentNameTv = studentDetailsDialog.findViewById(R.id.dialog_layout_student_name);
        final TextView studentEmailTv = studentDetailsDialog.findViewById(R.id.dialog_layout_student_email);
        final TextView studentEnrollmentTv = studentDetailsDialog.findViewById(R.id.dialog_layout_student_mobile);
        final TextView studentMobileTv = studentDetailsDialog.findViewById(R.id.dialog_layout_student_enrollment);
        final Button studentCancleBtn = studentDetailsDialog.findViewById(R.id.dialog_layout_student_cancle_btn);
        final Button studentAproveBtn = studentDetailsDialog.findViewById(R.id.dialog_layout_student_aprove_btn);


        mDatabase.child(AppConstant.FIREBASE_TABLE_STUDNET).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot student : dataSnapshot.getChildren()) {
                    if (student.child(AppConstant.FIREBASE_TABLE_EMAIL).getValue().equals(email)) {
                        Log.e("TAG-KEY", student.getKey());
                        studentPushKey = student.getKey();
                        studentName = student.child(AppConstant.FIREBASE_TABLE_FULLNAME).getValue().toString();
                        studentEmail = student.child(AppConstant.FIREBASE_TABLE_EMAIL).getValue().toString();
                        studentEnrollment = student.child(AppConstant.FIREBASE_TABLE_ENROLLMENT).getValue().toString();
                        studentMobile = student.child(AppConstant.FIREBASE_TABLE_MOBILE).getValue().toString();

                        studentNameTv.setText(studentName);
                        studentEmailTv.setText(studentEmail);
                        studentEnrollmentTv.setText(studentEnrollment);
                        studentMobileTv.setText(studentMobile);

                    }
                }
            }

            public void onCancelled(DatabaseError databaseError) {
            }
        });

        studentDetailsDialog.show();

        studentCancleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studentDetailsDialog.hide();
            }
        });

        studentAproveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studentDetailsDialog.dismiss();
                mDatabase.child(AppConstant.FIREBASE_TABLE_STUDNET).child(studentPushKey).child(AppConstant.FIREBASE_DB_ISACTIVATED).setValue(true);
                studentArrayList.clear();
                studentListArrayAdapter.notifyDataSetChanged();


            }
        });

    }
}
