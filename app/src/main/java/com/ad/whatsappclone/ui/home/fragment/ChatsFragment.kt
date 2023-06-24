package com.ad.whatsappclone.ui.home.fragment

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ad.whatsappclone.R
import com.ad.whatsappclone.databinding.FragmentChatsBinding
import com.ad.whatsappclone.models.Constraints
import com.ad.whatsappclone.models.Users
import com.ad.whatsappclone.ui.chat_details.ChatDetailsActivity
import com.ad.whatsappclone.ui.home.adapter.MainChatMessageAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ChatsFragment : Fragment() {
    private var _binding: FragmentChatsBinding? = null

    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    private val usersList: MutableList<Users> = ArrayList<Users>()
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentChatsBinding.inflate(inflater, container, false)

        val adapter = MainChatMessageAdapter(usersList)

        adapter.setOnChatListener(object : MainChatMessageAdapter.OnChatListener {
            override fun onImageClick(user: Users) {
                val dialog = Dialog(requireContext())
                dialog.setContentView(R.layout.dialog_chat_details)
                dialog.show()
            }

            override fun onMessageClick(user: Users) {
                val i = Intent(requireContext(), ChatDetailsActivity::class.java)
                i.putExtra(Constraints.USER_ID, user.userId)
                i.putExtra(Constraints.USER_PROFILE_PIC, user.userProfilePic)
                i.putExtra(Constraints.USER_NAME, user.userName)
                startActivity(i)
            }
        })

        binding.chatsMainRecycle.adapter = adapter
        binding.chatsMainRecycle.layoutManager = LinearLayoutManager(
            context,
            RecyclerView.VERTICAL,
            false
        )
        binding.chatsMainRecycle.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        database.reference
            .child(Constraints.USER_NODE)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    binding.progressBar.visibility = View.GONE
                    usersList.clear()
                    for (dataSnapshot in snapshot.children) {
                        val user: Users? = dataSnapshot.getValue(Users::class.java)
                        user?.userId = dataSnapshot.key
                        if (user != null && user.userId != FirebaseAuth.getInstance().uid) {
                            usersList.add(user)
                            adapter.notifyDataSetChanged()
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {}
            })
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.chat_tab_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.chat_tab_setting -> Toast.makeText(context, "Setting", Toast.LENGTH_SHORT).show()
        }

        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}