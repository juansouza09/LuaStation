package com.example.luastation.fragments.tabHome.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.luastation.databinding.FragmentMeusFreelasBinding
import com.example.luastation.models.Services
import com.example.luastation.adapters.MeusFreelasAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DatabaseError
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class MeusFreelasFragment : Fragment() {

    private var _binding: FragmentMeusFreelasBinding? = null
    private val binding get() = _binding!!

    var recyclerview: RecyclerView? = null
    var adapter: MeusFreelasAdapter? = null
    private val database by lazy {
        firebaseAuth.currentUser?.let {
            FirebaseDatabase.getInstance().getReference("Users").child(it.uid)
                .child("Meus Projetos")
        }
    }
    private val firebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMeusFreelasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        refreshFragment()
        lifecycleScope.launch { getFreelasData() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setRecyclerView() {
        recyclerview = binding.recyclerFreelas
        recyclerview!!.setHasFixedSize(true)
        recyclerview!!.layoutManager =
            LinearLayoutManager(this.requireContext(), RecyclerView.VERTICAL, false)
        adapter = MeusFreelasAdapter()
        recyclerview!!.adapter = adapter
    }

    private fun refreshFragment() {
        val swipe = binding.swipeToRefresh
        swipe.setOnRefreshListener {
            adapter?.submitList(listOf())
            lifecycleScope.launch { getFreelasData() }
            swipe.isRefreshing = false
        }
    }


    private suspend fun getFreelasData() = coroutineScope {
        database?.apply {
            addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val freelasArrayList = mutableListOf<Services?>()
                    if (snapshot.exists()) {
                        for (serviceSnapshot in snapshot.children) {
                            val freela = serviceSnapshot.getValue(Services::class.java)
                            freelasArrayList.add(freela)
                        }
                        adapter?.submitList(freelasArrayList)
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
