package br.solutionsjs.luastation.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import br.solutionsjs.luastation.ui.fragments.home.tabs.FreelancersFragment
import br.solutionsjs.luastation.ui.fragments.home.tabs.MyProjectsFragment
import br.solutionsjs.luastation.ui.fragments.home.tabs.ServicesFragment

class TabFragPageAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return NUMBER_OF_TABS
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ServicesFragment()
            1 -> FreelancersFragment()
            2 -> MyProjectsFragment()
            else -> Fragment()
        }
    }

    companion object {
        const val NUMBER_OF_TABS = 3
    }
}