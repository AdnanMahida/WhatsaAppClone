package com.ad.whatsappclone;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.ad.whatsappclone.databinding.ActivityChatInfoBinding;

public class ChatInfoActivity extends AppCompatActivity {
    ActivityChatInfoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}