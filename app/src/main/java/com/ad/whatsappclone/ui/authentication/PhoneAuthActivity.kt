package com.ad.whatsappclone.ui.authentication

import android.os.Bundle
import com.ad.whatsappclone.R
import com.ad.whatsappclone.databinding.ActivityPhoneAuthBinding
import com.ad.whatsappclone.ui.BaseActivity

class PhoneAuthActivity : BaseActivity() {
    private lateinit var binding: ActivityPhoneAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhoneAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setActionBarTitle(R.string.enter_your_phone_number)
    }
}