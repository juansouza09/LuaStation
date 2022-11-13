package com.example.luastation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.luastation.databinding.FragmentPerfilBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class PerfilFragment : Fragment() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var binding: FragmentPerfilBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPerfilBinding.inflate(inflater, container, false)
        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("Users")
        setInfo()
        return binding.root
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
