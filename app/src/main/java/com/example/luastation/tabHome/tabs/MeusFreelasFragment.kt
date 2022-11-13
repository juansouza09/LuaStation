package com.example.luastation.tabHome.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.luastation.databinding.FragmentMeusFreelasBinding
import com.example.luastation.firebase.models.Services
import com.example.luastation.tabHome.adapters.MeusFreelasAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MeusFreelasFragment : Fragment() {

    private lateinit var database: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth
    private var layoutManager: LinearLayoutManager? = null
    var recyclerview: RecyclerView? = null
    var binding: FragmentMeusFreelasBinding? = null
    var adapter: MeusFreelasAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMeusFreelasBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        firebaseAuth = FirebaseAuth.getInstance()
        getFreelasData()
    }

    private fun setRecyclerView() {
        recyclerview = binding?.recyclerFreelas
        recyclerview!!.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this.requireContext(), RecyclerView.VERTICAL, false)
        recyclerview!!.layoutManager = layoutManager
        adapter = MeusFreelasAdapter()
        recyclerview!!.adapter = adapter
    }

    private fun getFreelasData() {
        database = FirebaseDatabase.getInstance().getReference("Users")
            .child(firebaseAuth.currentUser!!.uid).child("Meus Projetos")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val freelasArrayList = mutableListOf<Services>()
                if (snapshot.exists()) {
                    for (serviceSnapshot in snapshot.children) {
                        val freela = serviceSnapshot.getValue(Services::class.java)
                        freelasArrayList.add(freela!!)
                    }
                    adapter?.submitList(freelasArrayList)
                    recyclerview?.visibility = View.VISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}
