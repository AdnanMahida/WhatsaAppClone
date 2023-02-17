package com.ad.whatsappclone.ui.home.fragment

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ad.whatsappclone.R
import com.ad.whatsappclone.databinding.FragmentStatusBinding
import com.ad.whatsappclone.models.Status
import com.ad.whatsappclone.ui.home.adapter.StatusAdapter

class StatusFragment : Fragment() {
    var binding: FragmentStatusBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentStatusBinding.inflate(inflater, container, false)
        initStatusList()
        return binding!!.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.status_tab_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.status_tab_menu_status_privacy -> Toast.makeText(
                context,
                "Sett",
                Toast.LENGTH_SHORT
            ).show()
        }
        return true
    }

    private fun initStatusList() {
        val statusList: MutableList<Status> = ArrayList()
        statusList.add(Status())
        val adapter = StatusAdapter(statusList)
        binding!!.statusFragmentRecycle.adapter = adapter
        binding!!.statusFragmentRecycle.layoutManager = LinearLayoutManager(context)
        binding!!.statusFragmentRecycle.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
    }
}