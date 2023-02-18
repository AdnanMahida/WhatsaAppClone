package com.ad.whatsappclone.ui.authentication

import android.content.Intent
import android.os.Bundle
import com.ad.whatsappclone.databinding.ActivityWelcomeBinding
import com.ad.whatsappclone.ui.BaseActivity

class WelcomeActivity : BaseActivity() {
    private lateinit var binding: ActivityWelcomeBinding

    override fun onStart() {
        super.onStart()
        if (introPrefData) {
            startActivity(Intent(this@WelcomeActivity, SignInActivity::class.java))
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideActionBar()
        binding.btnAgreeContinue.setOnClickListener {
            onAgreeBtnClick()
        }
    }

    private fun onAgreeBtnClick() {
        val intent = Intent(this@WelcomeActivity, PhoneAuthActivity::class.java)
        startActivity(intent)
        finish()
        saveIntroData()
    }

    // TODO:shared preference data
    private fun saveIntroData() {
        val sharedPreferences = applicationContext.getSharedPreferences("Welcome", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isWelcomeDone", true)
        editor.apply()
    }

    private val introPrefData: Boolean
        get() {
            val sharedPreferences = applicationContext.getSharedPreferences("Welcome", MODE_PRIVATE)
            return sharedPreferences.getBoolean("isWelcomeDone", false)
        }
}