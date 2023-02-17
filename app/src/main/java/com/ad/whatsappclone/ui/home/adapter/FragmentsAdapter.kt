package com.ad.whatsappclone.ui.home.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.ad.whatsappclone.ui.home.fragment.CallsFragment
import com.ad.whatsappclone.ui.home.fragment.CameraFragment
import com.ad.whatsappclone.ui.home.fragment.ChatsFragment
import com.ad.whatsappclone.ui.home.fragment.StatusFragment

class FragmentsAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> CameraFragment()
            2 -> StatusFragment()
            3 -> CallsFragment()
            else -> ChatsFragment()
        }
    }

    override fun getCount(): Int {
        return 4
    }

    override fun getPageTitle(position: Int): CharSequence? {
        var title: String? = null
        if (position == 1) {
            title = "CHATS"
        }
        if (position == 2) {
            title = "STATUS"
        }
        if (position == 3) {
            title = "CALLS"
        }
        return title
    }
}