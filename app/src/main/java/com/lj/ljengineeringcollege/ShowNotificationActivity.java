package com.lj.ljengineeringcollege;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowNotificationActivity extends AppCompatActivity implements NotificationAdapter.ItemListener {

    private RecyclerView notificationRecyclerView;
    private ArrayList<NotificationModel> notificationModelArrayList;
    private NotificationAdapter notificationAdapter;

    private Dialog notificationDetailsDialog;
    ProgressDialog progressDialog;

    //    Firebase Init
    private DatabaseReference DataRef;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_notification);
        DataRef = FirebaseDatabase.getInstance().getReference();

        initView();

        DataRef.child(AppConstant.FIREBASE_TABLE_NOTIFICATION).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot notificationModelDataSnapshot : dataSnapshot.getChildren()) {
                    Log.e("TAG_NOT", notificationModelDataSnapshot.getValue() + "");
                    NotificationModel notificationModel = notificationModelDataSnapshot.getValue(NotificationModel.class);
                    notificationModelArrayList.add(notificationModel);
                    Log.e("NOTE", notificationModelArrayList + "");
                    notificationAdapter.notifyDataSetChanged();

                }
                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        notificationAdapter = new NotificationAdapter(notificationModelArrayList, this, this);
        notificationRecyclerView.setAdapter(notificationAdapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        notificationRecyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration divider = new
                DividerItemDecoration(ShowNotificationActivity.this,
                DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(getBaseContext(),
                R.drawable.line_divider));
        notificationRecyclerView.addItemDecoration(divider);


    }

    private void initView() {
        notificationRecyclerView = findViewById(R.id.activity_show_notification_notification_list);
        progressDialog = new ProgressDialog(ShowNotificationActivity.this);

        progressDialog.setTitle("Notification");
        progressDialog.setMessage("Loading.....");
        progressDialog.show();
        notificationModelArrayList = new ArrayList<>();


    }

    @Override
    public void onItemClick(final NotificationModel item) {
        notificationDetailsDialog = new AppCompatDialog(ShowNotificationActivity.this, R.style.Theme_AppCompat_Light_Dialog_MinWidth);
        notificationDetailsDialog.setContentView(R.layout.dialog_layout_notification);
        notificationDetailsDialog.setTitle("Notification Details");

        final TextView notificationTitleTv = notificationDetailsDialog.findViewById(R.id.dialog_layout_notification_title);
        final TextView notificationMessageTv = notificationDetailsDialog.findViewById(R.id.dialog_layout_notification_message);
        final TextView notificationDepartmentTv = notificationDetailsDialog.findViewById(R.id.dialog_layout_notification_department);
        final TextView notificationFileNameTv = notificationDetailsDialog.findViewById(R.id.dialog_layout_notification_filename);
        final Button notificationCancleBtn = notificationDetailsDialog.findViewById(R.id.dialog_layout_notification_cancle_btn);
        final Button notificationDownloadBtn = notificationDetailsDialog.findViewById(R.id.dialog_layout_notification_download_btn);


        notificationTitleTv.setText(item.getTitle());
        notificationMessageTv.setText(item.getDescription());
        notificationDepartmentTv.setText(item.getDepartment());

        if (item.getFilename().isEmpty() && item.getFileUrl().isEmpty()) {
            notificationDownloadBtn.setVisibility(View.INVISIBLE);
            notificationFileNameTv.setVisibility(View.INVISIBLE);
        } else {
            notificationFileNameTv.setText(item.getFilename());
            notificationFileNameTv.setVisibility(View.VISIBLE);
            notificationDownloadBtn.setVisibility(View.VISIBLE);
        }

        notificationDetailsDialog.show();

        notificationCancleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificationDetailsDialog.dismiss();
            }
        });

        notificationDownloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(item.getFileUrl()));
                startActivity(intent);
                notificationDetailsDialog.dismiss();
            }
        });


    }
}
