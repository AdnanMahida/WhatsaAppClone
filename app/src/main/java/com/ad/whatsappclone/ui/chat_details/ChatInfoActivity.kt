package com.ad.whatsappclone.ui.chat_details

import android.os.Bundle
import com.ad.whatsappclone.databinding.ActivityChatInfoBinding
import com.ad.whatsappclone.ui.BaseActivity

class ChatInfoActivity : BaseActivity() {
    private lateinit var binding: ActivityChatInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}