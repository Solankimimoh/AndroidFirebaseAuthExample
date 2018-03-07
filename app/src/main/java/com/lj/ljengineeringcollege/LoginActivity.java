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
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.basgeekball.awesomevalidation.ValidationStyle.COLORATION;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    AwesomeValidation mAwesomeValidation = new AwesomeValidation(COLORATION);
    final String TAG = LoginActivity.class.getSimpleName();

    private EditText emailEd;
    private EditText pwdEd;
    private RadioGroup loginTypeRG;
    private Button loginBtn;
    private TextView gotoSingup;
    private TextView gotoForgotoPwd;
    private FirebaseAuth auth;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();

        initView();

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
        }

        String regexPassword = "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{8,}";

//        Validation Rule
        mAwesomeValidation.addValidation(LoginActivity.this, R.id.activity_signup_email_ed, android.util.Patterns.EMAIL_ADDRESS, R.string.val_err_email);
//        mAwesomeValidation.addValidation(LoginActivity.this, R.id.activity_signup_password_ed, regexPassword, R.string.err_password);


    }

    private void initView() {

        emailEd = findViewById(R.id.activity_login_email_ed);
        pwdEd = findViewById(R.id.activity_login_password_ed);
        loginTypeRG = findViewById(R.id.activity_login_type_rg);

        loginBtn = findViewById(R.id.activity_login_login_btn);
        gotoSingup = findViewById(R.id.activity_login_goto_signup_txt);
        gotoForgotoPwd = findViewById(R.id.activity_login_forgot_pwd_txt);

        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Login");
        progressDialog.setMessage("Checking credentials..");


        loginTypeRG.setOnCheckedChangeListener(this);

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
        String email = emailEd.getText().toString();
        String password = pwdEd.getText().toString();

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
                                checkIfEmailVerified();
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


//            Toast.makeText(this, "Validate the function", Toast.LENGTH_SHORT).show();
    }

    private void checkIfEmailVerified() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user.isEmailVerified()) {
            // user is verified, so you can finish this activity or send user to activity which you want.
            // finish();
            progressDialog.hide();
            emailEd.setText("");
            pwdEd.setText("");
            Toast.makeText(LoginActivity.this, "Successfully logged in", Toast.LENGTH_SHORT).show();
            Intent gotoHome = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(gotoHome);
        } else {
            // email is not verified, so just prompt the message to the user and restart this activity.
            // NOTE: don't forget to log out the user.
            progressDialog.hide();
            Toast.makeText(LoginActivity.this, "Please Verified your email", Toast.LENGTH_SHORT).show();
            FirebaseAuth.getInstance().signOut();
            //restart this activity
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.activity_login_faculty_rb:
                Toast.makeText(this, "FACULTY", Toast.LENGTH_SHORT).show();
                break;
            case R.id.activity_login_student_rb:
                Toast.makeText(this, "Student", Toast.LENGTH_SHORT).show();
                break;
        }
    }

}

