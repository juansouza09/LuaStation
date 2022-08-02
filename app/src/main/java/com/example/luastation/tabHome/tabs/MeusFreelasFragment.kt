package com.example.luastation.tabHome.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.luastation.databinding.FragmentMeusFreelasBinding
import com.example.luastation.tabHome.adapters.MeusFreelasAdapter

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
        recyclerview!!.adapter = adapter
    }

    private fun getFreelas(): List<Freelas> {
        return arrayListOf(
            Freelas("Aplicativo financeiro", "R$5000,00"),
            Freelas("Landing page restaurante", "R$600,00"),
            Freelas("Rede social", "R$4600,00"),
            Freelas("Aplicativo Delivery", "R$1320,00"),
            Freelas("Aplicativo lista de tarefas", "R$200,00")
        ).toList()
    }

    data class Freelas(val title: String, val price: String)
}
