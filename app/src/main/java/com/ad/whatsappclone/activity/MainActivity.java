package com.ad.whatsappclone.activity;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.ad.whatsappclone.R;
import com.ad.whatsappclone.adapter.FragmentsAdapter;
import com.ad.whatsappclone.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setElevation(0);
        mAuth = FirebaseAuth.getInstance();

        setUpTabLayout();

    }

    private void setUpTabLayout() {
        binding.mainViewPager.setAdapter(new FragmentsAdapter(getSupportFragmentManager()));
        binding.mainTabLayout.setupWithViewPager(binding.mainViewPager);
        binding.mainTabLayout.getTabAt(0).setIcon(R.drawable.ic_round_camera);
        binding.mainTabLayout.getTabAt(0).getIcon().setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
        LinearLayout layout = ((LinearLayout) ((LinearLayout) binding.mainTabLayout.getChildAt(0)).getChildAt(0));
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) layout.getLayoutParams();
        layoutParams.weight = 0.5f; // e.g. 0.5f
        layout.setLayoutParams(layoutParams);
        binding.mainViewPager.setCurrentItem(1);
//        binding.mainTabLayout.setScrollPosition(1,0f,true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.main_menu_search) {
            Toast.makeText(MainActivity.this, "Settings", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}