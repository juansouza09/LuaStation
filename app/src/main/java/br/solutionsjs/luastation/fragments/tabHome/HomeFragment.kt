package br.solutionsjs.luastation.fragments.tabHome

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.solutionsjs.luastation.activities.menu.MenuActivity
import br.solutionsjs.luastation.adapters.TabFragPageAdapter
import br.solutionsjs.luastation.databinding.FragmentHomeBinding
import br.solutionsjs.luastation.utils.ZoomOutPageTransformer
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTabs()
        addTabsToPageIndicator()
        listener()
        animation()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun listener() {
        binding.btnMenu.let {
            it.setOnClickListener {
                requireActivity().run {
                    startActivity(Intent(this, MenuActivity::class.java))
                }
            }
        }
    }

    private fun animation() {
        binding.viewPagerHome.apply {
            setPageTransformer(ZoomOutPageTransformer())
        }
    }

    private fun setTabs() {
        val fm = requireActivity().supportFragmentManager
        val adapter = TabFragPageAdapter(fm, lifecycle)
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
                    2 -> {
                        tab.text = "Meus Freelas"
                    }
                }
            }.attach()
        }
    }
}
