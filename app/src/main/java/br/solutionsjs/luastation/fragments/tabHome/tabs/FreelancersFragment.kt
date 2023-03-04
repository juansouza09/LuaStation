package br.solutionsjs.luastation.fragments.tabHome.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.solutionsjs.luastation.adapters.FreelancersAdapter
import br.solutionsjs.luastation.databinding.FragmentFreelancersBinding
import br.solutionsjs.luastation.models.Freelancers
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class FreelancersFragment : Fragment() {

    private var _binding: FragmentFreelancersBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    private val database by lazy {
        FirebaseDatabase.getInstance().getReference("Users")
    }
    private lateinit var myAdapter: FreelancersAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFreelancersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        lifecycleScope.launch { getFreelancersData() }
        refreshFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initAdapter() {
        recyclerView = binding.recyclerFreelancers
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.setHasFixedSize(true)
        myAdapter = FreelancersAdapter()
        recyclerView.adapter = myAdapter
    }

    private fun refreshFragment() {
        val swipe = binding.swipeToRefresh
        swipe.setOnRefreshListener {
            myAdapter.submitList(listOf())
            lifecycleScope.launch { getFreelancersData() }
            swipe.isRefreshing = false
        }
    }

    private suspend fun getFreelancersData() = coroutineScope {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val freelancersArrayList = mutableListOf<Freelancers?>()
                if (snapshot.exists()) {
                    for (freelancerSnapshot in snapshot.children) {
                        val freelancer = freelancerSnapshot.getValue(Freelancers::class.java)
                        freelancersArrayList.add(freelancer)
                    }
                    myAdapter.submitList(freelancersArrayList.filterNotNull())
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
