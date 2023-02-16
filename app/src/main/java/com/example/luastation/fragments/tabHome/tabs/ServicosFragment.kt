package com.example.luastation.fragments.tabHome.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.luastation.adapters.ServicesAdapter
import com.example.luastation.databinding.FragmentServicosBinding
import com.example.luastation.models.Services
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ServicosFragment : Fragment() {

    private var _binding: FragmentServicosBinding? = null
    private val binding get() = _binding!!

    private val database by lazy {
        FirebaseDatabase.getInstance().getReference("Services")
    }

    val myAdapter = ServicesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentServicosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getServiceData()
        setupUi()
        refreshFragment()
    }

    private fun setupUi() {
        binding.recyclerServicos.layoutManager =
            LinearLayoutManager(this.requireContext(), RecyclerView.VERTICAL, false)
        binding.recyclerServicos.adapter = myAdapter
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
                        myAdapter.submitList(servicesArrayList.filter { services -> services.status.toString() == SERVICE_STATUS })
                        binding.recyclerServicos.visibility = View.VISIBLE
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

    companion object {
        const val SERVICE_STATUS = "Em aberto"
    }
}
