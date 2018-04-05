
package com.lj.ljengineeringcollege;

import android.app.ProgressDialog;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GalleryActivity extends AppCompatActivity implements GalleryAdapter.ItemListener {

    private RecyclerView recyclerView;
    private ArrayList<GalleryModel> galleryModelArrayList;
    private GalleryAdapter galleryAdapter;
    private ProgressDialog progressDialog;


    //    Firebase Init
    private DatabaseReference DataRef;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        DataRef = FirebaseDatabase.getInstance().getReference();


        initView();

        DataRef.child("gallery").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("GALLERY", dataSnapshot.getChildren() + "");
                Log.e("GALLERY", DataRef.getRef() + "");
                Log.e("GALLERY", DataRef.getParent() + "");
                Log.e("GALLERY", DataRef.getDatabase() + "");
                Log.e("GALLERY", DataRef.getKey() + "");

                for (DataSnapshot galleryModelDataSnapeshot : dataSnapshot.getChildren()) {
                    GalleryModel galleryModel = galleryModelDataSnapeshot.getValue(GalleryModel.class);
                    Log.e("GALLERY", galleryModel.getImg());
                    galleryModelArrayList.add(galleryModel);
                    galleryAdapter.notifyDataSetChanged();
                }
                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("ERROR", databaseError.getMessage());
//                Toast.makeText(FacultyDetailsActivity.this, "EROR" + databaseError.getDetails(), Toast.LENGTH_SHORT).show();
            }
        });

        galleryAdapter = new GalleryAdapter(GalleryActivity.this, galleryModelArrayList, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(GalleryActivity.this, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(galleryAdapter);


    }

    private void initView() {
        recyclerView = findViewById(R.id.activity_gallery_recycleview_gallery);
        progressDialog = new ProgressDialog(GalleryActivity.this);

        progressDialog.setTitle("Faculty Details");
        progressDialog.setMessage("Loading.....");
        progressDialog.show();
        galleryModelArrayList = new ArrayList<>();
    }

    @Override
    public void onItemClick(GalleryModel item, int position) {

        Bundle bundle = new Bundle();
        bundle.putSerializable("images", galleryModelArrayList);
        bundle.putInt("position", position);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        SlideshowDialogFragment newFragment = SlideshowDialogFragment.newInstance();
        newFragment.setArguments(bundle);
        newFragment.show(ft, "slideshow");

    }
}
