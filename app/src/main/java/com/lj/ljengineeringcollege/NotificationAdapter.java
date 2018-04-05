package com.lj.ljengineeringcollege;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHoler> {


    ArrayList<NotificationModel> notificationModelArrayList;
    Context context;
    protected ItemListener itemListener;

    public NotificationAdapter(ArrayList<NotificationModel> notificationModelArrayList, Context context, ItemListener itemListener) {
        this.notificationModelArrayList = notificationModelArrayList;
        this.context = context;
        this.itemListener = itemListener;
    }

    public class ViewHoler extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView notificationTitleTv;
        public TextView notificationMessageTv;
        public ImageView notificationAttachmentImg;

        NotificationModel notificationModel;

        public ViewHoler(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            notificationTitleTv = itemView.findViewById(R.id.row_layout_notification_title_tv);
            notificationMessageTv = itemView.findViewById(R.id.row_layout_notification_message_tv);
            notificationAttachmentImg = itemView.findViewById(R.id.row_layout_notification_list_attachment_img);


        }

        public void setData(NotificationModel item) {
            this.notificationModel = item;

            notificationTitleTv.setText(item.getTitle());
            notificationMessageTv.setText(item.getDescription());

            if (notificationModel.getFileUrl().isEmpty()) {
                notificationAttachmentImg.setVisibility(View.INVISIBLE);
            } else {
                notificationAttachmentImg.setVisibility(View.VISIBLE);
            }


        }

        @Override
        public void onClick(View v) {
            if (itemListener != null) {
                itemListener.onItemClick(notificationModel);
            }
        }
    }

    @Override
    public ViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_layout_notification_list, parent, false);

        return new ViewHoler(view);
    }

    @Override
    public void onBindViewHolder(ViewHoler holder, int position) {
        holder.setData(notificationModelArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return notificationModelArrayList.size();
    }

    public interface ItemListener {
        void onItemClick(NotificationModel item);
    }

}

