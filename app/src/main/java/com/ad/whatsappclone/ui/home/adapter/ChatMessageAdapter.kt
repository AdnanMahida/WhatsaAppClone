package com.ad.whatsappclone.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ad.whatsappclone.R
import com.ad.whatsappclone.models.ChatMessages
import com.google.firebase.auth.FirebaseAuth

class ChatMessageAdapter(private val chatMessages: MutableList<ChatMessages?>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == SENDER_VIEW_TYPE) {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.recycle_sender, parent, false)
            SenderViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.recycle_receiver, parent, false)
            ReceiverViewHolder(view)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (chatMessages[position]?.messageId == FirebaseAuth.getInstance().uid) {
            SENDER_VIEW_TYPE
        } else {
            RECEIVER_VIEW_TYPE
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = chatMessages[position]

        if (holder is SenderViewHolder)
            holder.senderMessage.text = message?.message
        else if (holder is ReceiverViewHolder)
            holder.receiverMessage.text = message?.message

    }

    override fun getItemCount() = chatMessages.size


    inner class ReceiverViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var receiverMessage: TextView
        private var receiverTime: TextView

        init {
            receiverMessage = itemView.findViewById(R.id.txtReceiverMsg)
            receiverTime = itemView.findViewById(R.id.txtReceiverTime)
        }
    }

    inner class SenderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var senderMessage: TextView
        private var senderTime: TextView

        init {
            senderMessage = itemView.findViewById(R.id.txtSenderMsg)
            senderTime = itemView.findViewById(R.id.txtSenderTime)
        }
    }

    companion object {
        private const val SENDER_VIEW_TYPE = 0
        private const val RECEIVER_VIEW_TYPE = 1
    }
}