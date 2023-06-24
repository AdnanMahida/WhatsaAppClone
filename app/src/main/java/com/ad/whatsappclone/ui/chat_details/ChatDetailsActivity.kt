package com.ad.whatsappclone.ui.chat_details

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ad.whatsappclone.R
import com.ad.whatsappclone.databinding.ActivityChatDetailsBinding
import com.ad.whatsappclone.models.ChatMessages
import com.ad.whatsappclone.models.Constraints
import com.ad.whatsappclone.ui.BaseActivity
import com.ad.whatsappclone.ui.home.adapter.ChatMessageAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import java.util.*

class ChatDetailsActivity : BaseActivity() {
    private lateinit var binding: ActivityChatDetailsBinding
    private var database: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideActionBar()

        binding.chatDetailsLinearBack.setOnClickListener { finish() }

        mAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        val senderId = mAuth!!.uid
        val receiverId = intent.getStringExtra(Constraints.USER_ID)
        val userName = intent.getStringExtra(Constraints.USER_NAME)
        val userProfilePic = intent.getStringExtra(Constraints.USER_PROFILE_PIC)

        binding.chatDetailsTxtUserName.text = userName
        Picasso.get().load(userProfilePic).placeholder(R.drawable.ic_person_512dp)
            .into(binding.chatDetailsTxtProfile)

        val chatMessagesList: MutableList<ChatMessages?> = ArrayList()

        val adapter = ChatMessageAdapter(
            chatMessagesList,
        )
        binding.chatDetailsRecycleView.adapter = adapter
        binding.chatDetailsRecycleView.layoutManager =
            LinearLayoutManager(this@ChatDetailsActivity, RecyclerView.VERTICAL, false)
        //        binding.chatDetailsRecycleView.smoothScrollToPosition(adapter.getItemCount() - 1);
        val senderRoom = senderId + receiverId
        val receiverRoom = receiverId + senderId
        database?.reference?.child(Constraints.CHATS_NODE)
            ?.child(senderRoom)?.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    chatMessagesList.clear()
                    for (dataSnapshot in snapshot.children) {
                        val message = dataSnapshot.getValue(ChatMessages::class.java)
                        chatMessagesList.add(message)
                    }
                    adapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {}
            })
        binding.btnSendMsg.setOnClickListener {
            if (binding.edtMessage.text != null && binding.edtMessage.text.toString()
                    .isNotEmpty()
            ) {
                val message = binding.edtMessage.text.toString()
                val messages = ChatMessages(senderId, message)
                messages.timestamp = Date().time
                binding.edtMessage.setText("")
                database!!.reference.child(Constraints.CHATS_NODE).child(senderRoom)
                    .push()
                    .setValue(messages)
                    .addOnSuccessListener {
                        database!!.reference.child(Constraints.CHATS_NODE)
                            .child(receiverRoom)
                            .push()
                            .setValue(messages)
                            .addOnSuccessListener { }
                    }
            }
        }
    }
}