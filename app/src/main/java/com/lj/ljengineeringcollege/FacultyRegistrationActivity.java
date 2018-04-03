package com.lj.ljengineeringcollege;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FacultyRegistrationActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    //    Component
    private EditText fulllnameEd;
    private EditText emailEd;
    private EditText passwordEd;
    private EditText mobileEd;
    private Spinner departmnetSp;
    private TextView gotoLogin;
    private Button facultySignupBtn;

    //    Faculty String Variable

    private String fullname;
    private String email;
    private String password;
    private String mobile;
    private String department;

    //    Firebase
    private FirebaseAuth auth;
    private DatabaseReference mDatabase;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_registration);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        // Write a message to the database
        mDatabase = FirebaseDatabase.getInstance().getReference(AppConstant.FIREBASE_TABLE_FACULTY);

        initView();

    }

    private void initView() {
        fulllnameEd = findViewById(R.id.activity_faculty_registration_name_ed);
        emailEd = findViewById(R.id.activity_faculty_registration_email_ed);
        passwordEd = findViewById(R.id.activity_faculty_registration_password_ed);
        mobileEd = findViewById(R.id.activity_faculty_registration_mobile_ed);
        departmnetSp = findViewById(R.id.activity_faculty_registration_department_spinner);
        gotoLogin = findViewById(R.id.activity_faculty_registration_already);
        facultySignupBtn = findViewById(R.id.activity_faculty_registration_btn);


        //        Listener Initilization
        facultySignupBtn.setOnClickListener(this);
        gotoLogin.setOnClickListener(this);
        departmnetSp.setOnItemSelectedListener(this);

        //        Progressbar Dailog

        progressDialog = new ProgressDialog(FacultyRegistrationActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Signup");
        progressDialog.setMessage("Creating account");
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.activity_faculty_registration_already:
                finish();
                break;
            case R.id.activity_faculty_registration_btn:
                signupFaculty();
                break;
        }
    }

    private void signupFaculty() {

        progressDialog.show();

        fullname = fulllnameEd.getText().toString().trim();
        email = emailEd.getText().toString().trim();
        password = passwordEd.getText().toString().trim();
        mobile = mobileEd.getText().toString().trim();


        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(FacultyRegistrationActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                Log.e("TAG", "CREATE USER");
                if (!task.isSuccessful()) {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(FacultyRegistrationActivity.this, "User with this email already exist.", Toast.LENGTH_SHORT).show();
                        progressDialog.hide();
                    }
                } else {
                    String userId = auth.getCurrentUser().getUid();
                    Log.e("TAG", "INSERT USER");
                    mDatabase.child(userId).setValue(new FacultyModel(fullname,
                            email,
                            password,
                            mobile,
                            department,
                            true
                    ), new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            if (databaseError != null) {
                                Toast.makeText(FacultyRegistrationActivity.this, databaseError.toString(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(FacultyRegistrationActivity.this, "Success !  your account Created!", Toast.LENGTH_SHORT).show();
                                Intent gotoLogin = new Intent(FacultyRegistrationActivity.this, LoginActivity.class);
                                startActivity(gotoLogin);
                                progressDialog.hide();
                                finish();
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        department = adapterView.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
