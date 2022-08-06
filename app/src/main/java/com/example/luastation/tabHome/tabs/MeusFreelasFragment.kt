package com.example.luastation.tabHome.tabs

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.luastation.DetalhesActivity
import com.example.luastation.databinding.FragmentMeusFreelasBinding
import com.example.luastation.tabHome.adapters.MeusFreelasAdapter
import com.example.luastation.tabHome.adapters.ServicosAdapter

class MeusFreelasFragment : Fragment() {

    var recyclerview: RecyclerView? = null
    var layoutManager: LinearLayoutManager? = null
    var binding: FragmentMeusFreelasBinding? = null
    var adapter: MeusFreelasAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMeusFreelasBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
    }

    private fun setRecyclerView() {
        recyclerview = binding?.recyclerFreelas
        recyclerview!!.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this.requireContext(), RecyclerView.VERTICAL, false)
        recyclerview!!.layoutManager = layoutManager
        adapter = MeusFreelasAdapter(getFreelas())
        adapter!!.setOnItemClickListener(object : MeusFreelasAdapter.onItemClickListener {
            override fun onItemClick() {
                val intent = Intent(requireContext(), DetalhesActivity::class.java)
                startActivity(intent)
            }
        })
        recyclerview!!.adapter = adapter
    }

    private fun getFreelas(): List<Freelas> {
        return arrayListOf(
            Freelas("Aplicativo financeiro", "R$2100,00"),
            Freelas("Landing page restaurante", "R$400,00"),
            Freelas("Rede social", "R$2500,00")
        ).toList()
    }

    data class Freelas(val title: String, val price: String)
}
