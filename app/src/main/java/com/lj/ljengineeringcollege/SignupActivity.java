package com.lj.ljengineeringcollege;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private EditText fullNameEd;
    private EditText emailEd;
    private EditText pwdEd;
    private EditText enrollmentEd;
    private EditText mobileEd;
    private Spinner departmentSp;
    private Spinner semesterSp;
    private TextView alreadyRegisteredTv;
    private TextView facultyRegistrationTv;
    private Button signupBtn;

    //   Student String Variables
    private String fullname;
    private String email;
    private String password;
    private String enrollment;
    private String mobile;
    private String department;
    private String semester;

    //    Firebase
    private FirebaseAuth auth;
    private DatabaseReference mDatabase;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference(AppConstant.FIREBASE_TABLE_STUDNET);

        initView();

    }

    private void initView() {

        //Initilization of widgets

        fullNameEd = findViewById(R.id.activity_faculty_registration_name_ed);
        emailEd = findViewById(R.id.activity_faculty_registration_email_ed);
        pwdEd = findViewById(R.id.activity_faculty_registration_password_ed);
        enrollmentEd = findViewById(R.id.activity_signup_enrollment_ed);
        mobileEd = findViewById(R.id.activity_faculty_registration_mobile_ed);
        departmentSp = findViewById(R.id.activity_faculty_registration_department_spinner);
        semesterSp = findViewById(R.id.activity_signup_semester_spinner);

        alreadyRegisteredTv = findViewById(R.id.activity_signup_already_registered_txt);
        facultyRegistrationTv = findViewById(R.id.activity_signup_faculty_registration_tv);
        signupBtn = findViewById(R.id.activity_faculty_registration_btn);

        progressDialog = new ProgressDialog(SignupActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Singup");
        progressDialog.setMessage("Creating account");
//        Listener implement
        alreadyRegisteredTv.setOnClickListener(this);
        facultyRegistrationTv.setOnClickListener(this);
        signupBtn.setOnClickListener(this);

        departmentSp.setOnItemSelectedListener(this);
        semesterSp.setOnItemSelectedListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_signup_already_registered_txt:
                finish();
                break;
            case R.id.activity_faculty_registration_btn:
                singupStudent();
                break;
            case R.id.activity_signup_faculty_registration_tv:
                Intent gotoFacultyRegistration = new Intent(SignupActivity.this, FacultyRegistrationActivity.class);
                startActivity(gotoFacultyRegistration);
                finish();
                break;
        }
    }

    private void singupStudent() {

        progressDialog.show();

        fullname = fullNameEd.getText().toString().trim();
        email = emailEd.getText().toString().trim();
        password = pwdEd.getText().toString().trim();
        enrollment = enrollmentEd.getText().toString().trim();
        mobile = mobileEd.getText().toString().trim();

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(SignupActivity.this, "User with this email already exist.", Toast.LENGTH_SHORT).show();
                                progressDialog.hide();
                            }
                        } else {
                            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            String userId = user.getUid();

                            mDatabase.child(userId)
                                    .setValue(new StudentModel(fullname
                                                    , email
                                                    , password
                                                    , enrollment
                                                    , mobile
                                                    , department
                                                    , semester, false),
                                            new DatabaseReference.CompletionListener() {
                                                @Override
                                                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                                                    if (databaseError != null) {
                                                        Toast.makeText(SignupActivity.this, databaseError.toString(), Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        Toast.makeText(SignupActivity.this, "Success ! Faculty will Verify your account !", Toast.LENGTH_SHORT).show();
                                                        finish();
                                                        progressDialog.hide();

                                                    }
                                                }
                                            });
                        }
                    }
                });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        final Spinner spinner = (Spinner) adapterView;
        switch (spinner.getId()) {
            case R.id.activity_faculty_registration_department_spinner:
                department = spinner.getSelectedItem().toString();
                break;
            case R.id.activity_signup_semester_spinner:
                semester = spinner.getSelectedItem().toString();
                break;
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


}
