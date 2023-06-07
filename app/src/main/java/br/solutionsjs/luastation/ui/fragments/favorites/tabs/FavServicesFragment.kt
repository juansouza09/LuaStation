package br.solutionsjs.luastation.ui.fragments.favorites.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import br.solutionsjs.luastation.databinding.FragmentFavServicesBinding
import br.solutionsjs.luastation.domain.FavoriteViewModel
import br.solutionsjs.luastation.ui.adapters.FavServicesAdapter

class FavServicesFragment : Fragment() {

    private var _binding: FragmentFavServicesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavoriteViewModel by viewModels()

    private lateinit var myAdapter: FavServicesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavServicesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getFavoriteServices()
        viewModel.favoriteServices.observe(viewLifecycleOwner) { favoriteServices ->
            myAdapter.submitList(favoriteServices)
        }
        setUpRV()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpRV() {
        binding.rvServices.layoutManager = LinearLayoutManager(requireContext())
        myAdapter = FavServicesAdapter()
        binding.rvServices.adapter = myAdapter
    }
}
