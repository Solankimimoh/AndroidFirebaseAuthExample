package com.lj.ljengineeringcollege;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText fullNameEd;
    private EditText emailEd;
    private EditText pwdEd;
    private EditText enrollmentEd;
    private EditText mobileEd;
    private TextView alreadyRegisteredTv;
    private Button signupBtn;
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
        mDatabase = database.getReference("users");

        initView();

    }

    private void initView() {

        //Initilization of widgets

        fullNameEd = findViewById(R.id.activity_signup_name_ed);
        emailEd = findViewById(R.id.activity_signup_email_ed);
        pwdEd = findViewById(R.id.activity_signup_password_ed);
        enrollmentEd = findViewById(R.id.activity_signup_enrollment_ed);
        mobileEd = findViewById(R.id.activity_signup_mobile_ed);
        alreadyRegisteredTv = findViewById(R.id.activity_signup_already_registered_txt);
        signupBtn = findViewById(R.id.activity_signup_btn);

        progressDialog = new ProgressDialog(SignupActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Singup");
        progressDialog.setMessage("Creating account");
//        Listener implement
        alreadyRegisteredTv.setOnClickListener(this);
        signupBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_signup_already_registered_txt:
                finish();
                break;
            case R.id.activity_signup_btn:
                singupUser();
                break;
        }
    }

    private void singupUser() {

        progressDialog.show();

        final String userFullName = fullNameEd.getText().toString().trim();
        final String userEmail = emailEd.getText().toString().trim();
        final String userPassword = pwdEd.getText().toString().trim();
        final String userEnrollment = enrollmentEd.getText().toString().trim();
        final String userMobile = mobileEd.getText().toString().trim();

        auth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(SignupActivity.this, "User with this email already exist.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            String userId = user.getUid();

                            mDatabase.child(userId)
                                    .setValue(new Users(userFullName,
                                            userEmail,
                                            userPassword,
                                            userEnrollment,
                                            userMobile), new DatabaseReference.CompletionListener() {
                                        @Override
                                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                            if (databaseError != null) {
                                                Toast.makeText(SignupActivity.this, databaseError.toString(), Toast.LENGTH_SHORT).show();
                                            } else {

                                                progressDialog.setMessage("Sending Verification Mail");
                                                user.sendEmailVerification().addOnCompleteListener(SignupActivity.this, new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Toast.makeText(SignupActivity.this, "Success ! Check mail for verification", Toast.LENGTH_SHORT).show();
                                                            progressDialog.hide();
                                                        } else {
                                                            
                                                            Log.e("Success", "No" + task.getException());
                                                        }
                                                    }
                                                });
                                            }
                                        }
                                    });
                        }
                    }
                });
    }

}
