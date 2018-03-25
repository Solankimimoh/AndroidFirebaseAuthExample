package com.lj.ljengineeringcollege;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import static com.basgeekball.awesomevalidation.ValidationStyle.COLORATION;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private Button backBtn;
    private Button resetPasswordBtn;
    private EditText forgotEmailEd;
    AwesomeValidation mAwesomeValidation = new AwesomeValidation(COLORATION);
    private FirebaseAuth auth;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        auth = FirebaseAuth.getInstance();
        initView();
        String regexPassword = "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{8,}";

//        Validation Rule
        mAwesomeValidation.addValidation(ForgotPasswordActivity.this, R.id.activity_forgot_email, android.util.Patterns.EMAIL_ADDRESS, R.string.val_err_email);

    }

    private void initView() {
        forgotEmailEd = findViewById(R.id.activity_forgot_email);
        backBtn = findViewById(R.id.activity_forgot_btn_back);
        resetPasswordBtn = findViewById(R.id.activity_forgot_btn_reset_password);
        resetPasswordBtn.setOnClickListener(this);
        backBtn.setOnClickListener(this);
        progressDialog = new ProgressDialog(ForgotPasswordActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Reset Password");
        progressDialog.setMessage("Resend password email sending...");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_forgot_btn_reset_password:
                resetPassword();
                break;
            case R.id.activity_forgot_btn_back:
                finish();
                break;
        }
    }

    private void resetPassword() {
        if (mAwesomeValidation.validate()) {
            progressDialog.show();
            final String email = forgotEmailEd.getText().toString();
            auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        progressDialog.hide();
                        Toast.makeText(ForgotPasswordActivity.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            });
        }

    }
}
