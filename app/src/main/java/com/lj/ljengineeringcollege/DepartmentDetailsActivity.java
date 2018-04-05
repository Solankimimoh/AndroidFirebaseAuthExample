package com.lj.ljengineeringcollege;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;

public class DepartmentDetailsActivity extends AppCompatActivity implements RecyclerViewAdapter.ItemListener {

    private RecyclerView recyclerView;
    private ArrayList<HomeMenuItemModel> arrayList;
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depart_details);

        initView();

        intent = getIntent();

        Toast.makeText(this, "" + intent.getStringExtra("KEY_DEPARTMENT"), Toast.LENGTH_SHORT).show();

        arrayList = new ArrayList<>();
//        arrayList.add(new HomeMenuItemModel(getString(R.string.details), R.drawable.vector_info, "#303F9F"));
        arrayList.add(new HomeMenuItemModel(getString(R.string.faculty), R.drawable.vector_user, "#1976D2"));

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(DepartmentDetailsActivity.this, arrayList, this);
        recyclerView.setAdapter(adapter);

        AutoFitGridLayoutManager layoutManager = new AutoFitGridLayoutManager(this, 500);
        recyclerView.setLayoutManager(layoutManager);


    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.activity_depart_details);


    }

    @Override
    public void onItemClick(HomeMenuItemModel item) {
        if (intent != null) {
            Intent gotoFacultyDetails = new Intent(DepartmentDetailsActivity.this, FacultyDetailsActivity.class);
            gotoFacultyDetails.putExtra("KEY_DEPARTMENT", intent.getStringExtra("KEY_DEPARTMENT"));
            startActivity(gotoFacultyDetails);
        }
    }

}
