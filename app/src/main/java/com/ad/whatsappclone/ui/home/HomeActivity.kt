package com.ad.whatsappclone.ui.home

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.ad.whatsappclone.R
import com.ad.whatsappclone.ui.home.adapter.FragmentsAdapter
import com.ad.whatsappclone.databinding.ActivityHomeBinding
import com.ad.whatsappclone.ui.BaseActivity
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : BaseActivity() {

    private lateinit var binding: ActivityHomeBinding
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.elevation = 0f

        mAuth = FirebaseAuth.getInstance()
        setUpTabLayout()
        binding.mainViewPager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                if (position == 0) {
                    if (supportActionBar != null) {
                        supportActionBar!!.hide()
                        binding.mainTabLayout.visibility = View.GONE
                    }
                } else {
                    if (supportActionBar != null) {
                        supportActionBar!!.show()
                        binding.mainTabLayout.visibility = View.VISIBLE
                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    private fun setUpTabLayout() {
        binding.mainViewPager.adapter = FragmentsAdapter(supportFragmentManager)
        binding.mainTabLayout.setupWithViewPager(binding.mainViewPager)
        binding.mainTabLayout.getTabAt(0)?.setIcon(R.drawable.ic_round_camera)
        binding.mainTabLayout.getTabAt(0)?.icon?.setColorFilter(
            resources.getColor(android.R.color.white),
            PorterDuff.Mode.SRC_IN
        )
        val layout =
            (binding.mainTabLayout.getChildAt(0) as LinearLayout).getChildAt(0) as LinearLayout
        val layoutParams = layout.layoutParams as LinearLayout.LayoutParams
        layoutParams.weight = 0.5f // e.g. 0.5f
        layout.layoutParams = layoutParams
        binding.mainViewPager.currentItem = 1
        // binding.mainTabLayout.setScrollPosition(1,0f,true);
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.main_menu_search) {
            Toast.makeText(this@HomeActivity, getString(R.string.settings), Toast.LENGTH_SHORT)
                .show()
        }
        return true
    }

    override fun onBackPressed() {
        if (binding.mainTabLayout.selectedTabPosition != 1) {
            binding.mainViewPager.currentItem = 1
        } else {
            super.onBackPressed()
        }
    }
}