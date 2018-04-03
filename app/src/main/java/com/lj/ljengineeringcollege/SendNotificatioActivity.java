package com.lj.ljengineeringcollege;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class SendNotificatioActivity extends AppCompatActivity implements View.OnClickListener {


    //This is the pic pdf code used in file chooser
    final static int PICK_PDF_CODE = 2342;

    //component init
    private EditText notificationTitleEd;
    private EditText notificationDescriptioEd;
    private EditText notificationFileNameEd;
    private Button chooseFileBtn;
    private Button sendNotificationBtn;
    private TextView notificationFileNameTv;
    private ProgressBar notficationProgressBar;
    private TextView textViewStatus;


    private Uri selectedFileIntent;

    private String departmentName = null;

    //The firebase objects for storage and database
    private StorageReference mStorageReference;
    private DatabaseReference mDatabase;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_notificatio);
        //getting firebase objects
        mDatabase = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        mStorageReference = FirebaseStorage.getInstance().getReference();

        initView();
        getFacultyDepartmentName();


    }

    private void initView() {

        notificationTitleEd = findViewById(R.id.activity_send_notification_title_ed);
        notificationDescriptioEd = findViewById(R.id.activity_send_notification_description_ed);
        notificationFileNameEd = findViewById(R.id.activity_send_notification_file_name_ed);
        chooseFileBtn = findViewById(R.id.activity_send_notification_choose_file_btn);
        sendNotificationBtn = findViewById(R.id.activity_send_notification_send_btn);
        notificationFileNameTv = findViewById(R.id.activity_send_notification_file_status_tv);
        notficationProgressBar = findViewById(R.id.notification_progressbar);

        textViewStatus = (TextView) findViewById(R.id.textViewStatus);


        chooseFileBtn.setOnClickListener(this);
        sendNotificationBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.activity_send_notification_choose_file_btn:
                Toast.makeText(this, "CHOOSE FILE", Toast.LENGTH_SHORT).show();
                getPDF();
                break;

            case R.id.activity_send_notification_send_btn:
                sendNotificationToStudent();
                break;


        }


    }

    private void sendNotificationToStudent() {
        if (selectedFileIntent != null) {
            Toast.makeText(this, "" + getContentResolver().getType(selectedFileIntent), Toast.LENGTH_SHORT).show();
            uploadFile(selectedFileIntent);
        } else {
            final String title = notificationTitleEd.getText().toString().trim();
            final String description = notificationDescriptioEd.getText().toString().trim();

            NotificationModel notificationModel = new NotificationModel(departmentName, title, description, "", "");
            mDatabase.child(AppConstant.FIREBASE_TABLE_NOTIFICATION).child(mDatabase.push().getKey()).setValue(notificationModel, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    if (databaseError != null) {
                        Toast.makeText(SendNotificatioActivity.this, databaseError.toString(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SendNotificatioActivity.this, "Success ! Notification Send without Attachment!", Toast.LENGTH_SHORT).show();
                        sendFCMPush();
                        finish();
                    }
                }
            });
        }
    }


    private void sendFCMPush() {

        final String Legacy_SERVER_KEY = "AIzaSyAJErnfgVv26GRuqppdYvOSho93YaNGwjE";
        String msg =  notificationTitleEd.getText().toString().trim();
        String title =notificationDescriptioEd.getText().toString().trim();
        //  String token = FCM_RECEIVER_TOKEN;

        JSONObject obj = null;
        JSONObject objData = null;
        JSONObject dataobjData = null;

        try {
            obj = new JSONObject();
            objData = new JSONObject();

            objData.put("body", msg);
            objData.put("title", title);
            objData.put("sound", "default");
            objData.put("icon", "icon_name"); //   icon_name image must be there in drawable
            // objData.put("tag", token);

            objData.put("priority", "high");

            dataobjData = new JSONObject();
            dataobjData.put("text", msg);
            dataobjData.put("title", title);

            final String topicname = departmentName.replace(" ", "_");

            obj.put("to", "/topics/" + topicname);
            //obj.put("to", token);
            obj.put("priority", "high");

            obj.put("notification", objData);
            obj.put("data", dataobjData);
            Log.e("!_@rj@_@@_PASS:>", obj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, "https://fcm.googleapis.com/fcm/send", obj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("!_@@_SUCESS", response + "");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("!_@@_Errors--", error + "");
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "key=" + Legacy_SERVER_KEY);
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        int socketTimeout = 1000 * 60;// 60 seconds
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsObjRequest.setRetryPolicy(policy);
        requestQueue.add(jsObjRequest);
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


    //this function will get the pdf from the storage
    private void getPDF() {
        //for greater than lolipop versions we need the permissions asked on runtime
        //so if the permission is not available user will go to the screen to allow storage permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            return;
        } //creating an intent for file chooser

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            return;
        } //creating an intent for file chooser

        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_PDF_CODE);
    }


    //this method is uploading the file
    //the code is same as the previous tutorial
    //so we are not explaining it
    private void uploadFile(final Uri data) {
        notficationProgressBar.setVisibility(View.VISIBLE);

        final StorageReference sRef = FirebaseStorage.getInstance().getReference().child(AppConstant.STORAGE_PATH_UPLOADS_NOTIFICATION + System.currentTimeMillis() + ".pdf");
        sRef.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @SuppressWarnings("VisibleForTests")
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        notficationProgressBar.setVisibility(View.GONE);
                        textViewStatus.setText("File upload successfully");
                        getContentResolver().getType(data);

                        final String title = notificationTitleEd.getText().toString().trim();
                        final String description = notificationDescriptioEd.getText().toString().trim();
                        final String filename = notificationFileNameEd.getText().toString().trim();
                        final String fileUrl = taskSnapshot.getDownloadUrl().toString();

                        NotificationModel notificationModel = new NotificationModel(departmentName, title, description, filename, fileUrl);
                        mDatabase.child(AppConstant.FIREBASE_TABLE_NOTIFICATION).child(mDatabase.push().getKey()).setValue(notificationModel, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                if (databaseError != null) {
                                    Toast.makeText(SendNotificatioActivity.this, databaseError.toString(), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(SendNotificatioActivity.this, "Success ! Notification Send.!", Toast.LENGTH_SHORT).show();
                                    sendFCMPush();
                                    finish();
                                }
                            }
                        });

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @SuppressWarnings("VisibleForTests")
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                        double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        Log.e("TAG UPLOAD", taskSnapshot.getBytesTransferred() + "  " + progress + "====" + (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount());
                        textViewStatus.setText("" + progress + "% Uploading...");
                    }
                });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //when the user choses the file
        if (requestCode == PICK_PDF_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            //if a file is selected
            if (data.getData() != null) {
                //uploading the file
                File file = new File(data.getData().getPathSegments().toString());
                notificationFileNameTv.setText(file.getName().substring(0, file.getName().length() - 1));
                selectedFileIntent = data.getData();
            } else {
                Toast.makeText(this, "No file chosen", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
