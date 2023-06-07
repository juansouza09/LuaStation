package br.solutionsjs.luastation.ui.fragments.favorites.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import br.solutionsjs.luastation.databinding.FragmentFavFreelancersBinding
import br.solutionsjs.luastation.domain.FavoriteViewModel
import br.solutionsjs.luastation.ui.adapters.FavFreelancersAdapter

class FavFreelancersFragment : Fragment() {

    private var _binding: FragmentFavFreelancersBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavoriteViewModel by viewModels()

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
        viewModel.getFavoriteFreelancers()
        viewModel.favoriteFreelancers.observe(viewLifecycleOwner) { favoriteFreelancers ->
            myAdapter.submitList(favoriteFreelancers)
        }
        setUpRV()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpRV() {
        binding.rvFreelancers.layoutManager = GridLayoutManager(requireContext(), 2)
        myAdapter = FavFreelancersAdapter()
        binding.rvFreelancers.adapter = myAdapter
    }
}
