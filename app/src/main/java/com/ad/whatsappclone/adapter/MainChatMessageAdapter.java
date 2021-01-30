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
import com.ad.whatsappclone.models.Users;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainChatMessageAdapter extends RecyclerView.Adapter<MainChatMessageAdapter.ViewHolder> {
    private List<Users> usersList = new ArrayList<>();
    private Context mContext;

    public MainChatMessageAdapter(List<Users> usersList, Context mContext) {
        this.usersList = usersList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycle_main_chat_message, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Users user = usersList.get(position);
        Picasso.get().load(user.getUserProfilePic()).placeholder(R.drawable.ic_person_512dp).into(holder.image);
        holder.title.setText(user.getUserName());
        holder.itemView.setOnClickListener(view -> {
            Intent i = new Intent(mContext, ChatDetailsActivity.class);
            i.putExtra(Constraints.USER_ID, user.getUserId());
            i.putExtra(Constraints.USER_PROFILE_PIC, user.getUserProfilePic());
            i.putExtra(Constraints.USER_NAME, user.getUserName());
            mContext.startActivity(i);
        });

        holder.image.setOnClickListener(view -> {
            Dialog dialog = new Dialog(mContext);
            dialog.setContentView(R.layout.dialog_chat_details);
            dialog.show();
        });
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView title, lastMessage;

        public ViewHolder(View view) {
            super(view);

            image = view.findViewById(R.id.mainChatProfilePic);
            title = view.findViewById(R.id.mainChatTitle);
            lastMessage = view.findViewById(R.id.mainChatLastMessage);


        }

    }
}
