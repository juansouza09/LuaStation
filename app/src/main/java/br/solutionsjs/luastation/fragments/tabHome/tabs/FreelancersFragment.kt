package br.solutionsjs.luastation.fragments.tabHome.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import br.solutionsjs.luastation.adapters.FreelancersAdapter
import br.solutionsjs.luastation.databinding.FragmentFreelancersBinding
import br.solutionsjs.luastation.models.Freelancers
import com.google.android.gms.ads.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class FreelancersFragment : Fragment() {

    private var _binding: FragmentFreelancersBinding? = null
    private val binding get() = _binding!!

    private val database by lazy {
        FirebaseDatabase.getInstance().getReference("Users")
    }
    private lateinit var myAdapter: FreelancersAdapter
    private lateinit var mAdView: AdView

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
        setupAd()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupAd() {
        MobileAds.initialize(requireContext()) {}
        mAdView = binding.adView
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
        mAdView.adListener = object : AdListener() {
            override fun onAdClicked() {
                Toast.makeText(requireContext(), "Clicou", Toast.LENGTH_SHORT).show()
            }

            override fun onAdClosed() {
                Toast.makeText(requireContext(), "Retorne para o app", Toast.LENGTH_SHORT).show()
            }

            override fun onAdFailedToLoad(adError: LoadAdError) {
                // Code to be executed when an ad request fails.
            }

            override fun onAdImpression() {
                // Code to be executed when an impression is recorded
                // for an ad.
            }

            override fun onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            override fun onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }
        }
    }

    private fun initAdapter() {
        binding.recyclerFreelancers.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerFreelancers.setHasFixedSize(true)
        myAdapter = FreelancersAdapter()
        binding.recyclerFreelancers.adapter = myAdapter
    }

    private fun refreshFragment() {
        with(binding.swipeToRefresh) {
            setOnRefreshListener {
                myAdapter.submitList(listOf())
                lifecycleScope.launch { getFreelancersData() }
                isRefreshing = false
            }
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
                    myAdapter.submitList(
                        freelancersArrayList.filterNotNull().filter { it.cpf_cnpj?.length == 14 }
                    )
                    binding.recyclerFreelancers.isVisible = true
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
