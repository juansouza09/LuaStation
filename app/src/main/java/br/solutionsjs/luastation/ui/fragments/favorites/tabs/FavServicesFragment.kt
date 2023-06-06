package br.solutionsjs.luastation.ui.fragments.favorites.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import br.solutionsjs.luastation.ui.adapters.FavServicesAdapter
import br.solutionsjs.luastation.databinding.FragmentFavServicesBinding
import br.solutionsjs.luastation.data.models.Services
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class FavServicesFragment : Fragment() {

    private var _binding: FragmentFavServicesBinding? = null
    private val binding get() = _binding!!

    private lateinit var database: DatabaseReference
    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }

    private lateinit var myAdapter: FavServicesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavServicesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        getServiceData()
        refreshFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initAdapter() {
        binding.recyclerServicos.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerServicos.setHasFixedSize(true)
        myAdapter = FavServicesAdapter()
        binding.recyclerServicos.adapter = myAdapter
    }

    private fun refreshFragment() {
        with(binding.swipeToRefresh) {
            setOnRefreshListener {
                myAdapter.submitList(listOf())
                getServiceData()
                isRefreshing = false
            }
        }
    }

    private fun getServiceData() {
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            database =
                FirebaseDatabase.getInstance().getReference("Users").child((firebaseUser.uid))
                    .child("ServicosFav")
        }
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val servicesArrayList = mutableListOf<Services?>()
                if (servicesArrayList.isEmpty()) {
                    binding.recyclerServicos.isVisible = false
                }
                if (snapshot.exists()) {
                    for (serviceSnapshot in snapshot.children) {
                        val service = serviceSnapshot.getValue(Services::class.java)
                        servicesArrayList.add(service)
                    }
                    myAdapter.submitList(servicesArrayList)
                    binding.recyclerServicos.isVisible = true
                }
            }

            override fun onCancelled(error: DatabaseError) {
                binding.recyclerServicos.isVisible = false
            }
        })
    }
}
