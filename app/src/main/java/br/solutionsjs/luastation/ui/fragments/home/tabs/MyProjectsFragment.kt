package br.solutionsjs.luastation.ui.fragments.home.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.solutionsjs.luastation.databinding.FragmentMyProjectsBinding
import br.solutionsjs.luastation.domain.HomeViewModel
import br.solutionsjs.luastation.ui.adapters.MyProjectsAdapter
import com.google.android.gms.ads.*

class MyProjectsFragment : Fragment() {

    private var _binding: FragmentMyProjectsBinding? = null
    private val binding get() = _binding!!

    private lateinit var myAdapter: MyProjectsAdapter
    private lateinit var mAdView: AdView
    private val viewModel: HomeViewModel by viewModels()

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
        viewModel.getMyProjects()
        viewModel.myProjects.observe(viewLifecycleOwner) { myProjects ->
            myAdapter.submitList(myProjects)
        }
        setUpRV()
        setUpAds()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpAds() {
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

    private fun setUpRV() {
        binding.rvMyProjects.layoutManager =
            LinearLayoutManager(this.requireContext(), RecyclerView.VERTICAL, false)
        myAdapter = MyProjectsAdapter()
        binding.rvMyProjects.adapter = myAdapter
    }
}