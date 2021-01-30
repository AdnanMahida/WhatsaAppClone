package com.ad.whatsappclone.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.ad.whatsappclone.databinding.ActivityWelcomeBinding;

public class WelcomeActivity extends AppCompatActivity {
    ActivityWelcomeBinding binding;

    @Override
    protected void onStart() {
        super.onStart();
        if (getIntroPrefData()) {
            startActivity(new Intent(WelcomeActivity.this, SignInActivity.class));
            finish();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWelcomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    public void onAgreeBtnClick(View view) {
        Intent intent = new Intent(WelcomeActivity.this, SignInActivity.class);
        startActivity(intent);
        finish();
        saveIntroData();
    }

    // TODO:shared preference data
    private void saveIntroData() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("Welcome", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isWelcomeDone", true);
        editor.apply();
    }

    private boolean getIntroPrefData() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("Welcome", MODE_PRIVATE);
        return sharedPreferences.getBoolean("isWelcomeDone", false);
    }
}