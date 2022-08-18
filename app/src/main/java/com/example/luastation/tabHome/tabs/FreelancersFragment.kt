package com.example.luastation.tabHome.tabs

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.luastation.DetalhesActivity
import com.example.luastation.firebase.models.Freelancers
import com.example.luastation.databinding.FragmentFreelancersBinding
import com.example.luastation.tabHome.adapters.FreelancersAdapter
import com.google.firebase.database.*

class FreelancersFragment : Fragment() {

    private lateinit var binding: FragmentFreelancersBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var freelancersArrayList: ArrayList<Freelancers>
    private lateinit var database: DatabaseReference
    private lateinit var myAdapter: FreelancersAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFreelancersBinding.inflate(inflater, container, false)
        recyclerView = binding.recyclerFreelancers
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)
        freelancersArrayList = arrayListOf<Freelancers>()
        myAdapter = FreelancersAdapter(freelancersArrayList)
        recyclerView.adapter = myAdapter
        getFreelancersData()
        return binding.root
    }

    private fun getFreelancersData() {
        database = FirebaseDatabase.getInstance().getReference("Users")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (freelancerSnapshot in snapshot.children) {
                        val freelancer = freelancerSnapshot.getValue(Freelancers::class.java)
                        freelancersArrayList.add(freelancer!!)
                    }
                    myAdapter.setOnItemClickListener(object :
                            FreelancersAdapter.onItemClickListener {
                            override fun onItemClick() {
                                val intent = Intent(requireContext(), DetalhesActivity::class.java)
                                startActivity(intent)
                            }
                        })
                    recyclerView.visibility = View.VISIBLE
                    binding.progressBar2.visibility = View.GONE
                    myAdapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}
