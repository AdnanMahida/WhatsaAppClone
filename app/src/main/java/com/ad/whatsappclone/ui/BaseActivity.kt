package com.ad.whatsappclone.ui

import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {
    fun hideActionBar() {
        supportActionBar?.hide()
    }

    fun setActionBarTitle(@StringRes string: Int) {
        supportActionBar?.setTitle(string)
    }
}