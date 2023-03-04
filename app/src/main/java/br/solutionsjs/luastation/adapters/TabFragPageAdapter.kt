package br.solutionsjs.luastation.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import br.solutionsjs.luastation.fragments.tabHome.tabs.FreelancersFragment
import br.solutionsjs.luastation.fragments.tabHome.tabs.MeusFreelasFragment
import br.solutionsjs.luastation.fragments.tabHome.tabs.ServicosFragment

class TabFragPageAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return NUMBER_OF_TABS
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ServicosFragment()
            1 -> FreelancersFragment()
            2 -> MeusFreelasFragment()
            else -> Fragment()
        }
    }

    companion object {
        const val NUMBER_OF_TABS = 3
    }
}