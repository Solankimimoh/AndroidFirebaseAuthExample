package com.lj.ljengineeringcollege;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {

    private Context context;
    private ArrayList<GalleryModel> galleryModelArrayList;
    protected ItemListener mListener;
    int position;


    public GalleryAdapter(Context context, ArrayList<GalleryModel> galleryModelArrayList, ItemListener mListener) {
        this.context = context;
        this.galleryModelArrayList = galleryModelArrayList;
        this.mListener = mListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imageView;
        private GalleryModel galleryModel;

        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            imageView = itemView.findViewById(R.id.row_layout_gallery_imgview);
        }

        public void setData(GalleryModel item) {
            galleryModel = item;

            Log.e("TAG_NAME", item.getImg());

            Glide.with(context).load(item.getImg())
                    .placeholder(R.drawable.common_google_signin_btn_icon_light_focused)
                    .thumbnail(0.5f)
                    .crossFade()
                    .error(android.R.color.holo_red_light)
                    .fallback(android.R.color.holo_orange_light)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
//            relativeLayout.setBackgroundColor(Color.parseColor(item.color));

        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(galleryModel, position);
            }
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_layout_gallery, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(galleryModelArrayList.get(position));
        this.position = position;
    }

    @Override
    public int getItemCount() {
        return galleryModelArrayList.size();
    }


    public interface ItemListener {
        void onItemClick(GalleryModel item, int position);
    }

}
