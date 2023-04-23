package br.solutionsjs.luastation.fragments.fav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.solutionsjs.luastation.adapters.FavServicesAdapter
import br.solutionsjs.luastation.databinding.FragmentFavServicesBinding
import br.solutionsjs.luastation.models.Services
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class FavServicesFragment : Fragment() {

    private var _binding: FragmentFavServicesBinding? = null
    private val binding get() = _binding!!

    private lateinit var database: DatabaseReference
    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }

    private lateinit var recyclerview: RecyclerView
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
        recyclerview = binding.recyclerServicos
        recyclerview.layoutManager = LinearLayoutManager(requireContext())
        recyclerview.setHasFixedSize(true)
        myAdapter = FavServicesAdapter()
        recyclerview.adapter = myAdapter
    }

    private fun refreshFragment() {
        val swipe = binding.swipeToRefresh
        swipe.setOnRefreshListener {
            myAdapter.submitList(listOf())
            getServiceData()
            swipe.isRefreshing = false
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
                    binding.recyclerServicos.visibility = View.GONE
                }
                if (snapshot.exists()) {
                    for (serviceSnapshot in snapshot.children) {
                        val service = serviceSnapshot.getValue(Services::class.java)
                        servicesArrayList.add(service)
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
