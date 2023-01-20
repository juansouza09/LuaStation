package com.example.luastation.fragments.fav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.luastation.databinding.FragmentFreelancersFavBinding
import com.example.luastation.firebase.models.Freelancers
import com.example.luastation.tabHome.adapters.FreelancerFavAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class FreelancersFavFragment : Fragment() {

    private lateinit var binding: FragmentFreelancersFavBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var database: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var myAdapter: FreelancerFavAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFreelancersFavBinding.inflate(inflater, container, false)
        recyclerView = binding.recyclerFreelancers
        firebaseAuth = FirebaseAuth.getInstance()
        initAdapter()
        getFreelancersData()
        refreshFragment()
        return binding.root
    }

    private fun initAdapter() {
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.setHasFixedSize(true)
        myAdapter = FreelancerFavAdapter()
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
        val firebaseUser = firebaseAuth.currentUser
        database = FirebaseDatabase.getInstance().getReference("Users").child((firebaseUser!!.uid))
            .child("FreelancerFav")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val freelancersArrayList = mutableListOf<Freelancers>()
                freelancersArrayList.clear()
                if (freelancersArrayList.isEmpty()) {
                    binding.recyclerFreelancers.visibility = View.GONE
                }
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
                binding.recyclerFreelancers.visibility = View.GONE
            }
        })
    }
}