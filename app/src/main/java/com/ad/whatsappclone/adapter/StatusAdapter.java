package com.ad.whatsappclone.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ad.whatsappclone.R;
import com.ad.whatsappclone.activity.ChatDetailsActivity;
import com.ad.whatsappclone.models.Constraints;
import com.ad.whatsappclone.models.Status;
import com.ad.whatsappclone.models.Users;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.ViewHolder> {
    private List<Status> statusList = new ArrayList<>();
    private Context mContext;

    public StatusAdapter(List<Status> statusList, Context mContext) {
        this.statusList = statusList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycle_status, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return statusList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView title, lastMessage;

        public ViewHolder(View view) {
            super(view);




        }

    }
}
