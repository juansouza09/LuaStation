package br.solutionsjs.luastation.fragments.tabHome.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.solutionsjs.luastation.adapters.MyProjectsAdapter
import br.solutionsjs.luastation.databinding.FragmentMyProjectsBinding
import br.solutionsjs.luastation.models.Services
import com.google.android.gms.ads.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class MyProjectsFragment : Fragment() {

    private var _binding: FragmentMyProjectsBinding? = null
    private val binding get() = _binding!!

    val adapter = MyProjectsAdapter()
    private val database by lazy {
        firebaseAuth.currentUser?.let {
            FirebaseDatabase.getInstance().getReference("Users").child(it.uid)
                .child("Meus Projetos")
        }
    }
    private val firebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }
    private lateinit var mAdView: AdView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyProjectsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        refreshFragment()
        lifecycleScope.launch { getProjectsData() }
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

    private fun setRecyclerView() {
        binding.recyclerFreelas.setHasFixedSize(true)
        binding.recyclerFreelas.layoutManager =
            LinearLayoutManager(this.requireContext(), RecyclerView.VERTICAL, false)
        binding.recyclerFreelas.adapter = adapter
    }

    private fun refreshFragment() {
        with(binding.swipeToRefresh) {
            setOnRefreshListener {
                adapter.submitList(listOf())
                lifecycleScope.launch { getProjectsData() }
                isRefreshing = false
            }
        }
    }

    private suspend fun getProjectsData() = coroutineScope {
        database?.apply {
            addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val projectsArrayList = mutableListOf<Services?>()
                    if (snapshot.exists()) {
                        for (serviceSnapshot in snapshot.children) {
                            val project = serviceSnapshot.getValue(Services::class.java)
                            projectsArrayList.add(project)
                        }
                        adapter.submitList(projectsArrayList)
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
