package com.example.luastation.fragments.fav


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.luastation.databinding.FragmentFavMainBinding
import com.example.luastation.adapters.TabFragPageFavAdapter
import com.example.luastation.utils.ZoomOutPageTransformer
import com.google.android.material.tabs.TabLayoutMediator

class FavMainFragment : Fragment() {

    private var _binding: FragmentFavMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        _binding = FragmentFavMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        animation()
        setTabs()
        addTabsToPageIndicator()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun animation() {
        binding.viewPagerHome.setPageTransformer(ZoomOutPageTransformer())
    }

    private fun setTabs() {
        val fm = requireActivity().supportFragmentManager
        val adapter = TabFragPageFavAdapter(fm, lifecycle)
        binding.viewPagerHome.adapter = adapter
    }

    private fun addTabsToPageIndicator() {
        binding.let {
            TabLayoutMediator(
                it.tabLayout,
                it.viewPagerHome
            ) { tab, position ->
                when (position) {
                    0 -> {
                        tab.text = "Serviços"
                    }
                    1 -> {
                        tab.text = "Freelancers"
                    }
                }
            }.attach()
        }
    }
}
