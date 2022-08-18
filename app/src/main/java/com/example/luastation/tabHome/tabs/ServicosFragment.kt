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
import com.example.luastation.Services
import com.example.luastation.databinding.FragmentServicosBinding
import com.example.luastation.tabHome.adapters.ServicosAdapter
import com.google.firebase.database.*

class ServicosFragment : Fragment() {

    private lateinit var recyclerview: RecyclerView
    private lateinit var binding: FragmentServicosBinding
    private lateinit var servicesArrayList: ArrayList<Services>
    private lateinit var database: DatabaseReference
    private lateinit var myAdapter: ServicosAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentServicosBinding.inflate(inflater, container, false)
        recyclerview = binding.recyclerServicos
        recyclerview.layoutManager = LinearLayoutManager(requireContext())
        recyclerview.setHasFixedSize(true)
        servicesArrayList = arrayListOf<Services>()
        myAdapter = ServicosAdapter(servicesArrayList)
        recyclerview.adapter = myAdapter
        getServiceData()
        return binding.root
    }

    private fun getServiceData() {
        database = FirebaseDatabase.getInstance().getReference("Services")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                servicesArrayList.clear()
                if (snapshot.exists()) {
                    for (serviceSnapshot in snapshot.children) {
                        val service = serviceSnapshot.getValue(Services::class.java)
                        servicesArrayList.add(service!!)
                    }
                    myAdapter.setOnItemClickListener(object : ServicosAdapter.onItemClickListener {
                        override fun onItemClick() {
                            val intent = Intent(requireContext(), DetalhesActivity::class.java)
                            startActivity(intent)
                        }
                    })
                    myAdapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}
