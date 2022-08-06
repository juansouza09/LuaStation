package com.example.luastation.tabHome.tabs

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.luastation.PerfilContratanteActivity
import com.example.luastation.databinding.FragmentFreelancersBinding
import com.example.luastation.tabHome.adapters.FreelancersAdapter

class FreelancersFragment : Fragment() {

    private lateinit var binding: FragmentFreelancersBinding
    var recyclerView: RecyclerView? = null
    var adapter: FreelancersAdapter? = null
    var layoutManager: LinearLayoutManager? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFreelancersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
    }

    private fun setRecyclerView() {
        recyclerView = binding?.recyclerFreelancers
        recyclerView!!.setHasFixedSize(true)
        layoutManager = GridLayoutManager(this.requireContext(), 2)
        recyclerView!!.layoutManager = layoutManager
        adapter = FreelancersAdapter(getFreelancers())
        adapter!!.setOnItemClickListener(object : FreelancersAdapter.onItemClickListener {
            override fun onItemClick() {
                val intent = Intent(requireContext(), PerfilContratanteActivity::class.java)
                startActivity(intent)
            }
        })
        recyclerView!!.adapter = adapter
    }

    private fun getFreelancers(): List<Freelancer> {
        return arrayListOf(
            Freelancer("Maxsuel Souza", "maxsouza@email.com"),
            Freelancer("Juan Pablo", "juan.souza@email.com"),
            Freelancer("Fe Sottili", "fesottili@email.com"),
            Freelancer("Newana Vitoria", "newana@email.com"),
            Freelancer("Guilherme", "guilherme@email.com"),
            Freelancer("Maxsuel Souza", "maxsouza@email.com"),
            Freelancer("Juan Pablo", "juan.souza@email.com"),
            Freelancer("Fe Sottili", "fesottili@email.com"),
            Freelancer("Newana Vitoria", "newana@email.com"),
            Freelancer("Guilherme", "guilherme@email.com"),
            Freelancer("Maxsuel Souza", "maxsouza@email.com"),
            Freelancer("Juan Pablo", "juan.souza@email.com"),
            Freelancer("Fe Sottili", "fesottili@email.com"),
            Freelancer("Newana Vitoria", "newana@email.com"),
            Freelancer("Guilherme", "guilherme@email.com")
        ).toList()
    }

    data class Freelancer(val name: String, val email: String)
}
