package com.ad.whatsappclone.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ad.whatsappclone.R
import com.ad.whatsappclone.models.Status

class StatusAdapter(statusList: List<Status>) : RecyclerView.Adapter<StatusAdapter.ViewHolder>() {
    private var statusList: List<Status> = ArrayList()

    init {
        this.statusList = statusList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recycle_status, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {}
    override fun getItemCount(): Int {
        return statusList.size
    }

    inner class ViewHolder(view: View?) : RecyclerView.ViewHolder(
        view!!
    ) {
        private val image: ImageView? = null
        private val title: TextView? = null
        private val lastMessage: TextView? = null
    }
}