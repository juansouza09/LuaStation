package br.solutionsjs.luastation.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import br.solutionsjs.luastation.fragments.fav.FavFreelancersFragment
import br.solutionsjs.luastation.fragments.fav.FavServicesFragment

class TabFragPageFavAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return NUMBER_OF_TABS
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FavServicesFragment()
            1 -> FavFreelancersFragment()
            else -> Fragment()
        }
    }

    companion object {
        const val NUMBER_OF_TABS = 2
    }
}
