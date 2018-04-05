package com.lj.ljengineeringcollege;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {

    private Context context;
    private ArrayList<GalleryModel> galleryModelArrayList;
    int position;


    public GalleryAdapter(Context context, ArrayList<GalleryModel> galleryModelArrayList) {
        this.context = context;
        this.galleryModelArrayList = galleryModelArrayList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {

        private ImageView imageView;
        private GalleryModel galleryModel;

        public ViewHolder(View itemView) {
            super(itemView);

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




    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private GalleryAdapter.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final GalleryAdapter.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}
