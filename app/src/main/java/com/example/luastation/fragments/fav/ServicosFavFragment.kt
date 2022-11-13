package com.example.luastation.fragments.fav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.luastation.databinding.FragmentServicosFavBinding
import com.example.luastation.firebase.models.Services
import com.example.luastation.tabHome.adapters.FavoritosAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ServicosFavFragment : Fragment() {

    private lateinit var recyclerview: RecyclerView
    private lateinit var binding: FragmentServicosFavBinding
    private lateinit var database: DatabaseReference
    private lateinit var myAdapter: FavoritosAdapter
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentServicosFavBinding.inflate(inflater, container, false)
        recyclerview = binding.recyclerServicos
        firebaseAuth = FirebaseAuth.getInstance()
        initAdapter()
        getServiceData()
        refreshFragment()
        return binding.root
    }

    private fun initAdapter() {
        recyclerview.layoutManager = LinearLayoutManager(requireContext())
        recyclerview.setHasFixedSize(true)
        myAdapter = FavoritosAdapter()
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
        val firebaseUser = firebaseAuth.currentUser
        database = FirebaseDatabase.getInstance().getReference("Users").child((firebaseUser!!.uid))
            .child("ServicosFav")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val servicesArrayList = mutableListOf<Services>()
                servicesArrayList.clear()
                if (servicesArrayList.isEmpty()) {
                    binding.recyclerServicos.visibility = View.GONE
                }
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
                binding.recyclerServicos.visibility = View.GONE
            }
        })
    }
}
