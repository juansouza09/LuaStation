package com.example.luastation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.luastation.databinding.FragmentFavoritosBinding
import com.example.luastation.tabHome.adapters.FavoritosAdapter

class FavoritosFragment : Fragment() {

    private lateinit var binding: FragmentFavoritosBinding
    var recyclerView: RecyclerView? = null
    var adapter: FavoritosAdapter? = null
    var layoutManager: LinearLayoutManager? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
    }

    private fun setRecyclerView() {
        recyclerView = binding?.recyclerFavoritos
        recyclerView!!.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this.requireContext(), RecyclerView.VERTICAL, false)
        recyclerView!!.layoutManager = layoutManager
        adapter = FavoritosAdapter(getFavoritos())
        recyclerView!!.adapter = adapter
    }

    private fun getFavoritos(): List<Favoritos> {
        return arrayListOf(
            Favoritos("Aplicativo financeiro", "R$5000,00"),
            Favoritos("Landing page restaurante", "R$600,00"),
            Favoritos("Rede social", "R$4600,00"),
            Favoritos("Aplicativo Delivery", "R$1320,00"),
            Favoritos("Aplicativo lista de tarefas", "R$200,00")
        ).toList()
    }

    data class Favoritos(val title: String, val price: String)
}
