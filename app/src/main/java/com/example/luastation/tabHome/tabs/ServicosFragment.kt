package com.example.luastation.tabHome.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.luastation.databinding.FragmentServicosBinding
import com.example.luastation.tabHome.adapters.ServicosAdapter

class ServicosFragment : Fragment() {

    var recyclerview: RecyclerView? = null
    var layoutManager: LinearLayoutManager? = null
    var binding: FragmentServicosBinding? = null
    var adapter: ServicosAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentServicosBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
    }

    private fun setRecyclerView() {
        recyclerview = binding?.recyclerServicos
        recyclerview!!.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this.requireContext(), RecyclerView.VERTICAL, false)
        recyclerview!!.layoutManager = layoutManager
        adapter = ServicosAdapter(getServicos())
        recyclerview!!.adapter = adapter
    }

    private fun getServicos(): List<Servicos> {
        return arrayListOf(
            Servicos("Aplicativo financeiro", "R$5000,00"),
            Servicos("Landing page restaurante", "R$600,00"),
            Servicos("Rede social", "R$4600,00"),
            Servicos("Aplicativo Delivery", "R$1320,00"),
            Servicos("Aplicativo lista de tarefas", "R$200,00")
        ).toList()
    }

    data class Servicos(val title: String, val price: String)
}
