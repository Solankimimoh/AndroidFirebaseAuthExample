package com.lj.ljengineeringcollege;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class DepartmentListActivity extends AppCompatActivity implements RecyclerViewAdapter.ItemListener {

    private RecyclerView recyclerView;
    private ArrayList<HomeMenuItemModel> arrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_list);


        initView();

        arrayList = new ArrayList<>();
        arrayList.add(new HomeMenuItemModel(getString(R.string.dept_it), R.drawable.vector_it, "#303F9F"));
        arrayList.add(new HomeMenuItemModel(getString(R.string.dept_computer), R.drawable.vector_computer, "#1976D2"));
        arrayList.add(new HomeMenuItemModel(getString(R.string.dept_mechanical), R.drawable.vector_mechanical, "#0097A7"));
        arrayList.add(new HomeMenuItemModel(getString(R.string.dept_civil), R.drawable.vector_civil, "#0288D1"));
        arrayList.add(new HomeMenuItemModel(getString(R.string.dept_chemical), R.drawable.vector_chemical, "#512DA8"));
        arrayList.add(new HomeMenuItemModel(getString(R.string.dept_texttile), R.drawable.vector_texttile, "#7B1FA2"));

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(DepartmentListActivity.this, arrayList, this);
        recyclerView.setAdapter(adapter);

        AutoFitGridLayoutManager layoutManager = new AutoFitGridLayoutManager(this, 600);
        recyclerView.setLayoutManager(layoutManager);


    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.activity_department_list_recyleview);


    }

    @Override
    public void onItemClick(HomeMenuItemModel item) {
        final Intent gotoDepartmentDetails = new Intent(DepartmentListActivity.this, DepartmentDetailsActivity.class);
        gotoDepartmentDetails.putExtra("KEY_DEPARTMENT", item.text);
        startActivity(gotoDepartmentDetails);
    }
}
