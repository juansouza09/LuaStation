package br.solutionsjs.luastation.fragments.fav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.solutionsjs.luastation.models.Services
import br.solutionsjs.luastation.adapters.FavoritosAdapter
import br.solutionsjs.luastation.databinding.FragmentServicosFavBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference

class ServicosFavFragment : Fragment() {


    private var _binding: FragmentServicosFavBinding? = null
    private val binding get() = _binding!!

    private lateinit var database: DatabaseReference
    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }

    private lateinit var recyclerview: RecyclerView
    private lateinit var myAdapter: FavoritosAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentServicosFavBinding.inflate(inflater, container, false)
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
        myAdapter = FavoritosAdapter()
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
            database = FirebaseDatabase.getInstance().getReference("Users").child((firebaseUser.uid))
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