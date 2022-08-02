package com.example.luastation.tabHome

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.luastation.databinding.FragmentHomeBinding
import com.example.luastation.menusuperior.MenuActivity
import com.example.luastation.tabHome.adapters.TabFragPageAdapter
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnMenu.setOnClickListener {
            requireActivity().run {
                startActivity(Intent(this, MenuActivity::class.java))
                finish()
            }
        }

        setTabs()
        addTabsToPageIndicator()
    }

    private fun setTabs() {
        val fm = requireActivity().supportFragmentManager
        val adapter = TabFragPageAdapter(fm, lifecycle)
        binding?.let {
            it.viewPagerHome.adapter = adapter
        }
    }

    private fun addTabsToPageIndicator() {
        binding?.let {
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
                    2 -> {
                        tab.text = "Meus Freelas"
                    }
                }
            }.attach()
        }
    }
}
