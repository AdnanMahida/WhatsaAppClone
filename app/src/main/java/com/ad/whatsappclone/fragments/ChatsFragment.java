package com.ad.whatsappclone.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ad.whatsappclone.adapter.MainChatMessageAdapter;
import com.ad.whatsappclone.databinding.FragmentChatsBinding;
import com.ad.whatsappclone.models.Constraints;
import com.ad.whatsappclone.models.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatsFragment extends Fragment {

    public ChatsFragment() {
        // Required empty public constructor
    }

    FragmentChatsBinding binding;
    List<Users> usersList = new ArrayList<>();
    FirebaseDatabase database;

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentChatsBinding.inflate(inflater, container, false);
        MainChatMessageAdapter adapter = new MainChatMessageAdapter(usersList, getContext());
        binding.chatsMainRecycle.setAdapter(adapter);
        binding.chatsMainRecycle.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        database = FirebaseDatabase.getInstance();

        database.getReference().child(Constraints.USER_NODE).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Users user = dataSnapshot.getValue(Users.class);
                    user.setUserId(dataSnapshot.getKey());
                    usersList.add(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return binding.getRoot();
    }
}