package com.example.luastation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.luastation.databinding.FragmentNotificacoesBinding
import com.example.luastation.tabHome.adapters.NotificationAdapter

class NotificacaoFragment : Fragment() {
    private lateinit var binding: FragmentNotificacoesBinding
    var recyclerView: RecyclerView? = null
    var adapter: NotificationAdapter? = null
    var layoutManager: LinearLayoutManager? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotificacoesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
    }

    private fun setRecyclerView() {
        recyclerView = binding?.recyclerServicos
        recyclerView!!.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this.requireContext(), RecyclerView.VERTICAL, false)
        recyclerView!!.layoutManager = layoutManager
        adapter = NotificationAdapter(getNotification())
        recyclerView!!.adapter = adapter
    }

    private fun getNotification(): List<Notification> {
        return arrayListOf(
            Notification("Serviço aceito!"),
            Notification("Pagamento realizado!"),
            Notification("Serviço aceito!"),
            Notification("Solicitação recusada"),
            Notification("Veja freelas disponíveis!")
        ).toList()
    }

    data class Notification(val description: String)
}
