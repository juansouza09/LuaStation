package br.solutionsjs.luastation.fragments.fav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.solutionsjs.luastation.adapters.FavFreelancersAdapter
import br.solutionsjs.luastation.databinding.FragmentFavFreelancersBinding
import br.solutionsjs.luastation.models.Freelancers
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class FavFreelancersFragment : Fragment() {

    private var _binding: FragmentFavFreelancersBinding? = null
    private val binding get() = _binding!!

    private lateinit var database: DatabaseReference
    private val firebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var myAdapter: FavFreelancersAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavFreelancersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        getFreelancersData()
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
        myAdapter = FavFreelancersAdapter()
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
        if (firebaseUser != null) {
            database =
                FirebaseDatabase.getInstance().getReference("Users").child((firebaseUser.uid))
                    .child("FreelancerFav")
        }
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val freelancersArrayList = mutableListOf<Freelancers?>()
                if (freelancersArrayList.isEmpty()) {
                    binding.recyclerFreelancers.visibility = View.GONE
                }
                if (snapshot.exists()) {
                    for (freelancerSnapshot in snapshot.children) {
                        val freelancer = freelancerSnapshot.getValue(Freelancers::class.java)
                        freelancersArrayList.add(freelancer)
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
