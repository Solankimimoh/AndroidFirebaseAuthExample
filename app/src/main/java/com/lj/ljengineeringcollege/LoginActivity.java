package com.lj.ljengineeringcollege;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.basgeekball.awesomevalidation.ValidationStyle.COLORATION;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    final String TAG = LoginActivity.class.getSimpleName();
    AwesomeValidation mAwesomeValidation = new AwesomeValidation(COLORATION);

    private EditText emailEd;
    private EditText pwdEd;
    private RadioGroup loginTypeRg;
    private Button loginBtn;
    private TextView gotoSingup;
    private TextView gotoForgotoPwd;

    //    Firebase
    private FirebaseAuth auth;
    private ProgressDialog progressDialog;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

//    variable

    private String loginType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

//        if (auth.getCurrentUser() != null) {
//            auth.signOut();
//            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
//            finish();
//        }
        initView();


        initValidationRules();


    }

    private void initValidationRules() {
        String regexPassword = "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{8,}";
        mAwesomeValidation.addValidation(LoginActivity.this, R.id.activity_faculty_registration_email_ed, android.util.Patterns.EMAIL_ADDRESS, R.string.val_err_email);
        //        mAwesomeValidation.addValidation(LoginActivity.this, R.id.activity_signup_password_ed, regexPassword, R.string.err_password);
    }

    private void initView() {

        //Componenet Initlization
        emailEd = findViewById(R.id.activity_login_email_ed);
        pwdEd = findViewById(R.id.activity_login_password_ed);
        loginTypeRg = findViewById(R.id.activity_login_type_rg);
        loginBtn = findViewById(R.id.activity_login_login_btn);
        gotoSingup = findViewById(R.id.activity_login_goto_signup_txt);
        gotoForgotoPwd = findViewById(R.id.activity_login_forgot_pwd_txt);

        //ProgressDialog
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Login");
        progressDialog.setMessage("Checking credentials..");


        //Event Listener
        loginBtn.setOnClickListener(this);
        gotoSingup.setOnClickListener(this);
        gotoForgotoPwd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_login_login_btn:
                checkLogin();
                break;
            case R.id.activity_login_goto_signup_txt:
                Intent gotoSignupIntent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(gotoSignupIntent);
                break;
            case R.id.activity_login_forgot_pwd_txt:
                Intent gotoForgotPwd = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(gotoForgotPwd);
        }
    }

    private void checkLogin() {

        final String email = emailEd.getText().toString();
        final String password = pwdEd.getText().toString();

        if (loginTypeRg.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please Choose Login Type", Toast.LENGTH_SHORT).show();
            return;
        }


        if (mAwesomeValidation.validate()) {
            progressDialog.show();
            progressDialog.setMessage("Check Email ID and password...");

            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.e(TAG, "signInWithEmail:success");

                                progressDialog.setMessage("Email Verifying ....");
                                switch (loginTypeRg.getCheckedRadioButtonId()) {
                                    case R.id.activity_login_faculty_rb:
                                        CheckEmailIsVerified(AppConstant.FIREBASE_TABLE_FACULTY);
                                        break;
                                    case R.id.activity_login_student_rb:
                                        CheckEmailIsVerified(AppConstant.FIREBASE_TABLE_STUDNET);
                                        break;
                                }

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.e(TAG, "signInWithEmail:failure", task.getException());
                                progressDialog.hide();
                                Toast.makeText(LoginActivity.this, "Email and password Dosent match",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }


    }

    private void CheckEmailIsVerified(String firebaseTable) {

        databaseReference.child(firebaseTable).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (!dataSnapshot.hasChild(auth.getCurrentUser().getUid())) {
                    Toast.makeText(LoginActivity.this, "Credentials not match with login type", Toast.LENGTH_SHORT).show();
                    progressDialog.hide();
                } else {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        Log.e("TAG", data.child(AppConstant.FIREBASE_DB_ISACTIVATED).getValue().toString());
                        boolean status = Boolean.parseBoolean(data.child(AppConstant.FIREBASE_DB_ISACTIVATED).getValue().toString());
                        if (status) {
                            //do ur stuff
                            progressDialog.hide();
                        } else {
                            progressDialog.hide();
                            Toast.makeText(LoginActivity.this, "Email ID is not verified yet ! ", Toast.LENGTH_SHORT).show();

                            //do something
                        }
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(LoginActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        progressDialog.dismiss();
    }
}

