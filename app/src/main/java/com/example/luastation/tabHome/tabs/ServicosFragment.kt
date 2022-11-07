package com.example.luastation.tabHome.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.luastation.databinding.FragmentServicosBinding
import com.example.luastation.firebase.models.Services
import com.example.luastation.tabHome.adapters.NotificationAdapter
import com.example.luastation.tabHome.adapters.ServicosAdapter
import com.google.firebase.database.*

class ServicosFragment : Fragment() {

    private lateinit var recyclerview: RecyclerView
    private lateinit var binding: FragmentServicosBinding
    private lateinit var database: DatabaseReference
    private lateinit var myAdapter: ServicosAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentServicosBinding.inflate(inflater, container, false)
        recyclerview = binding.recyclerServicos
        initAdapter()
        getServiceData()
        refreshFragment()
        return binding.root
    }

    private fun initAdapter() {
        recyclerview.layoutManager = LinearLayoutManager(requireContext())
        recyclerview.setHasFixedSize(true)
        myAdapter = ServicosAdapter()
        recyclerview.adapter = myAdapter
    }

    private fun refreshFragment() {
        val swipe = binding.swipeToRefresh
        swipe.setOnRefreshListener {
            getServiceData()
            swipe.isRefreshing = false
        }
    }

    private fun getServiceData() {
        database = FirebaseDatabase.getInstance().getReference("Services")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val servicesArrayList = mutableListOf<Services>()
                if (snapshot.exists()) {
                    for (serviceSnapshot in snapshot.children) {
                        val service = serviceSnapshot.getValue(Services::class.java)
                        servicesArrayList.add(service!!)
                    }
                    myAdapter.submitList(servicesArrayList)
                    recyclerview.visibility = View.VISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}
