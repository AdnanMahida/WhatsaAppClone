package com.ad.whatsappclone.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ad.whatsappclone.R
import com.ad.whatsappclone.models.Users
import com.squareup.picasso.Picasso

class MainChatMessageAdapter(usersList: List<Users>) :
    RecyclerView.Adapter<MainChatMessageAdapter.ViewHolder>() {
    private var listener: OnChatListener? = null
    private var usersList: List<Users> = ArrayList()

    init {
        this.usersList = usersList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recycle_main_chat_message, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = usersList[position]

        Picasso.get().load(user.userProfilePic).placeholder(R.drawable.ic_person_512dp)
            .into(holder.image)
        holder.title.text = user.userName
        holder.lastMessage.text = user.lastMessage

        holder.itemView.setOnClickListener {
            listener?.onMessageClick(user)
        }
        holder.image.setOnClickListener {
            listener?.onImageClick(user)
        }
    }

    override fun getItemCount() = usersList.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView
        val title: TextView
        val lastMessage: TextView

        init {
            image = view.findViewById(R.id.mainChatProfilePic)
            title = view.findViewById(R.id.mainChatTitle)
            lastMessage = view.findViewById(R.id.mainChatLastMessage)
        }
    }

    interface OnChatListener {
        fun onImageClick(user: Users)
        fun onMessageClick(user: Users)
    }

    fun setOnChatListener(listener: OnChatListener) {
        this.listener = listener
    }
}