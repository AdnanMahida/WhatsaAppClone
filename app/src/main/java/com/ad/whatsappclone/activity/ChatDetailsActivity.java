package com.ad.whatsappclone.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.ad.whatsappclone.databinding.ActivityChatDetailsBinding;

public class ChatDetailsActivity extends AppCompatActivity {

    ActivityChatDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}