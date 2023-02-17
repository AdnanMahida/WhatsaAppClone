package com.ad.whatsappclone.ui

import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {
    fun hideActionBar() {
        supportActionBar?.hide()
    }
}