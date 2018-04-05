package com.lj.ljengineeringcollege;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;


public class FacultyDetailsAdapter extends RecyclerView.Adapter<FacultyDetailsAdapter.ViewHolder> {

    ArrayList<FacultyDetailsModel> mValues;
    Context mContext;
    protected ItemListener mListener;

    public FacultyDetailsAdapter(Context context, ArrayList<FacultyDetailsModel> values, ItemListener itemListener) {
        mValues = values;
        mContext = context;
        mListener = itemListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView nameTv;
        public TextView designationTv;
        public TextView qualificationTv;
        public TextView experienceTv;
        public TextView interestTv;
        public ImageView profileImg;
        FacultyDetailsModel item;

        public ViewHolder(View v) {

            super(v);

            v.setOnClickListener(this);
            nameTv = (TextView) v.findViewById(R.id.row_layout_faculty_details_faculty_name);
            designationTv = (TextView) v.findViewById(R.id.row_layout_faculty_details_designation_tv);
            qualificationTv = (TextView) v.findViewById(R.id.row_layout_faculty_details_qualification_tv);
            experienceTv = (TextView) v.findViewById(R.id.row_layout_faculty_details_experience_tv);
            interestTv = (TextView) v.findViewById(R.id.row_layout_faculty_details_interest_tv);
            profileImg = (ImageView) v.findViewById(R.id.row_layout_faculty_details_profile_img);
//            relativeLayout = (RelativeLayout) v.findViewById(R.id.row_layout_home_relativeLayout);

        }

        public void setData(FacultyDetailsModel item) {
            this.item = item;
            Log.e("TAG_NAME", item.getName());

            nameTv.setText(item.getName());
            designationTv.setText(item.getDesignation());
            qualificationTv.setText(item.getQualification());
            experienceTv.setText(item.getExperience());
            interestTv.setText(item.getInterest());

            Log.e("URL", item.getProfilepic() + "sdfsdfdfdfdfdf");
            Glide.with(mContext).load(item.getProfilepic())
                    .placeholder(R.drawable.common_google_signin_btn_icon_light_focused)
                    .crossFade()
                    .error(android.R.color.holo_red_light)
                    .fallback(android.R.color.holo_orange_light)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(profileImg);
//            relativeLayout.setBackgroundColor(Color.parseColor(item.color));

        }


        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onItemClick(item);
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.row_layout_faculty_details, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder Vholder, int position) {
        Vholder.setData(mValues.get(position));

    }

    @Override
    public int getItemCount() {

        return mValues.size();
    }

    public interface ItemListener {
        void onItemClick(FacultyDetailsModel item);
    }
}