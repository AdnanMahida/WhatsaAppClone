package com.ad.whatsappclone.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.ad.whatsappclone.R;
import com.ad.whatsappclone.databinding.ActivityChatDetailsBinding;
import com.ad.whatsappclone.models.Constraints;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class ChatDetailsActivity extends AppCompatActivity {

    ActivityChatDetailsBinding binding;
    FirebaseDatabase database;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        binding.chatDetailsLinearBack.setOnClickListener(view -> onBackPressed());

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        String senderId = mAuth.getUid();
        String receiverId = getIntent().getStringExtra(Constraints.USER_ID);
        String userName = getIntent().getStringExtra(Constraints.USER_NAME);
        String userProfilePic = getIntent().getStringExtra(Constraints.USER_PROFILE_PIC);

        binding.chatDetailsTxtUserName.setText(userName);
        Picasso.get().load(userProfilePic).placeholder(R.drawable.ic_person_512dp).into(binding.chatDetailsTxtProfile);


    }
}