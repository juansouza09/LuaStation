package com.example.luastation.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.luastation.databinding.FragmentHomeBinding
import com.example.luastation.databinding.FragmentNotificacoesBinding
import com.example.luastation.databinding.FragmentPerfilBinding
import com.example.luastation.menusuperior.MenuActivity

class NotificacaoFragment : Fragment() {
    private lateinit var binding: FragmentNotificacoesBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotificacoesBinding.inflate(inflater, container, false)
        return binding.root
    }
}