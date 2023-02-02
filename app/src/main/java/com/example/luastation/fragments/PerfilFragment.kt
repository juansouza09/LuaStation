package com.example.luastation.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.luastation.activities.login.LoginActivity
import com.example.luastation.databinding.FragmentPerfilBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DatabaseError

class PerfilFragment : Fragment() {

    private var _binding: FragmentPerfilBinding? = null
    private val binding get() = _binding!!

    private val firebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }
    private val database by lazy {
        FirebaseDatabase.getInstance().getReference("Users")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPerfilBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setInfo()
        setListeners()
    }

    private fun setListeners() {
        binding.imgSair.setOnClickListener {
            requireActivity().run {
                checkUser()
                firebaseAuth.signOut()
            }
        }
    }

    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setInfo() {
        val user = firebaseAuth.currentUser
        val userReference = database.child(user!!.uid)

        binding.perfilEmailText.text = user.email
        userReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                binding.perfilNameText.text = snapshot.child("name").value.toString()
                binding.textNameContratante.text = snapshot.child("name").value.toString()
                binding.perfilCpfCnpjText.text = snapshot.child("cpf_cnpj").value.toString()
                binding.perfilNascText.text = snapshot.child("dataNasc").value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}
