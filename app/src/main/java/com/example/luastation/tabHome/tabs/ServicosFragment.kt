package com.example.luastation.tabHome.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.luastation.databinding.FragmentServicosBinding
import com.example.luastation.firebase.models.Services
import com.example.luastation.tabHome.adapters.ServicosAdapter
import com.google.firebase.database.*

class ServicosFragment : Fragment() {

    private lateinit var recyclerview: RecyclerView
    private lateinit var binding: FragmentServicosBinding
    private val database by lazy {
        FirebaseDatabase.getInstance().getReference("Services")
    }
    private lateinit var myAdapter: ServicosAdapter
    private var layoutManager: LinearLayoutManager? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentServicosBinding.inflate(inflater, container, false)
        setRecyclerView()
        getServiceData()
        refreshFragment()
        return binding.root
    }

    private fun setRecyclerView() {
        recyclerview = binding.recyclerServicos
        recyclerview.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this.requireContext(), RecyclerView.VERTICAL, false)
        recyclerview.layoutManager = layoutManager
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
        database.apply {
            addValueEventListener(object : ValueEventListener {
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
                    Toast.makeText(
                        requireContext(),
                        "Infelizmente, não foi possível fazer a busca",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        }
    }
}
