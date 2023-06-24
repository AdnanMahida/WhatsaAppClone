package com.ad.whatsappclone.util.widget

import android.app.Dialog
import android.content.Context
import android.widget.TextView
import com.ad.whatsappclone.R

class AdProgressDialog(context: Context) {
    private val dialog: Dialog = Dialog(context)
    private var title: CharSequence? = null
    private var message: CharSequence? = null

    fun show() {
        dialog.setContentView(R.layout.dialog_progress_ad)
        if (!title.isNullOrEmpty()) {
            dialog.findViewById<TextView>(R.id.tvDialogTitle).text = title
        }
        if (!message.isNullOrEmpty()) {
            dialog.findViewById<TextView>(R.id.tvDialogTitle).text = message
        }
        dialog.show()
    }

    fun dismiss() {
        if (dialog.isShowing) {
            dialog.dismiss()
        }
    }

    fun setTitle(title: CharSequence) {
        this.title = title
    }

    fun setMessage(message: CharSequence) {
        this.message = message
    }
}