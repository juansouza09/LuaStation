package com.example.luastation.tabHome.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.luastation.databinding.FragmentFreelancersBinding
import com.example.luastation.firebase.models.Freelancers
import com.example.luastation.tabHome.adapters.FreelancersAdapter
import com.google.firebase.database.*

class FreelancersFragment : Fragment() {

    private lateinit var binding: FragmentFreelancersBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var database: DatabaseReference
    private lateinit var myAdapter: FreelancersAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFreelancersBinding.inflate(inflater, container, false)
        recyclerView = binding.recyclerFreelancers
        initAdapter()
        getFreelancersData()
        refreshFragment()
        return binding.root
    }

    private fun initAdapter() {
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.setHasFixedSize(true)
        myAdapter = FreelancersAdapter()
        recyclerView.adapter = myAdapter
    }

    private fun refreshFragment() {
        val swipe = binding.swipeToRefresh
        swipe.setOnRefreshListener {
            getFreelancersData()
            swipe.isRefreshing = false
        }
    }

    private fun getFreelancersData() {
        database = FirebaseDatabase.getInstance().getReference("Users")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val freelancersArrayList = mutableListOf<Freelancers>()
                if (snapshot.exists()) {
                    for (freelancerSnapshot in snapshot.children) {
                        val freelancer = freelancerSnapshot.getValue(Freelancers::class.java)
                        freelancersArrayList.add(freelancer!!)
                    }
                    myAdapter.submitList(freelancersArrayList)
                    recyclerView.visibility = View.VISIBLE
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
