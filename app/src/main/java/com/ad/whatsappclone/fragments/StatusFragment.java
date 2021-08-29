package com.ad.whatsappclone.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ad.whatsappclone.R;
import com.ad.whatsappclone.adapter.StatusAdapter;
import com.ad.whatsappclone.databinding.FragmentStatusBinding;
import com.ad.whatsappclone.models.Status;

import java.util.ArrayList;
import java.util.List;

public class StatusFragment extends Fragment {
    FragmentStatusBinding binding;

    public StatusFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentStatusBinding.inflate(inflater, container, false);
        initStatusList();
        return binding.getRoot();
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.status_tab_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.status_tab_menu_status_privacy:
                Toast.makeText(getContext(), "Sett", Toast.LENGTH_SHORT).show();
//                mAuth.signOut();
//                Intent intent = new Intent(MainActivity.this, SignInActivity.class);
//                startActivity(intent);
                break;
        }
        return true;
    }

    private void initStatusList() {
        List<Status> statusList = new ArrayList<>();
        statusList.add(new Status());
        StatusAdapter adapter = new StatusAdapter(statusList, getContext());
        binding.statusFragmentRecycle.setAdapter(adapter);
        binding.statusFragmentRecycle.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.statusFragmentRecycle.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

    }
}