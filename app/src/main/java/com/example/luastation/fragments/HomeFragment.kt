package com.example.luastation.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.luastation.databinding.FragmentHomeBinding
import com.example.luastation.menusuperior.MenuActivity

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.btnMenu.setOnClickListener {
            requireActivity().run{
                startActivity(Intent(this, MenuActivity::class.java))
                finish()
            }
        }

        return binding.root
    }
}