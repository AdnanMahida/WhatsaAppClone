package com.ad.whatsappclone.activity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ad.whatsappclone.R;
import com.ad.whatsappclone.adapter.ChatMessageAdapter;
import com.ad.whatsappclone.databinding.ActivityChatDetailsBinding;
import com.ad.whatsappclone.models.ChatMessages;
import com.ad.whatsappclone.models.Constraints;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

        final List<ChatMessages> chatMessagesList = new ArrayList<>();
        final ChatMessageAdapter adapter = new ChatMessageAdapter(chatMessagesList, ChatDetailsActivity.this);
        binding.chatDetailsRecycleView.setAdapter(adapter);
        binding.chatDetailsRecycleView.setLayoutManager(new LinearLayoutManager(ChatDetailsActivity.this));

        final String senderRoom = senderId + receiverId;
        final String receiverRoom = receiverId + senderId;

        database.getReference().child(Constraints.CHATS_NODE)
                .child(senderRoom)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        chatMessagesList.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            ChatMessages message = dataSnapshot.getValue(ChatMessages.class);
                            chatMessagesList.add(message);
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        binding.btnSendMsg.setOnClickListener(view -> {
            if (binding.edtMessage.getText() != null && binding.edtMessage.getText().toString().length() != 0) {
                String message = binding.edtMessage.getText().toString();
                final ChatMessages messages = new ChatMessages(senderId, message);
                messages.setTimestamp(new Date().getTime());
                binding.edtMessage.setText("");
                database.getReference().child(Constraints.CHATS_NODE).child(senderRoom)
                        .push()
                        .setValue(messages)
                        .addOnSuccessListener(aVoid -> {
                            database.getReference().child(Constraints.CHATS_NODE)
                                    .child(receiverRoom)
                                    .push()
                                    .setValue(messages)
                                    .addOnSuccessListener(aVoid1 -> {

                                    });
                        });
            }
        });
    }
}