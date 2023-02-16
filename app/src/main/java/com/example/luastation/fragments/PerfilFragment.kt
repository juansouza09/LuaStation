package com.example.luastation.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.luastation.activities.login.LoginActivity
import com.example.luastation.databinding.FragmentPerfilBinding
import com.example.luastation.models.Services
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PerfilFragment : Fragment() {

    private var _binding: FragmentPerfilBinding? = null
    private val binding get() = _binding!!

    private val firebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }
    private val database by lazy {
        FirebaseDatabase.getInstance().getReference("Users")
    }

    private val database2 by lazy {
        FirebaseDatabase.getInstance().getReference("Users").child(firebaseAuth.currentUser!!.uid).child("Meus Projetos")
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

        database2.apply {
            addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val freelasList = mutableListOf<Services>()
                    if (snapshot.exists()) {
                        for (serviceSnapshot in snapshot.children) {
                            val freela = serviceSnapshot.getValue(Services::class.java)
                            freelasList.add(freela!!)
                        }
                        setUi(freelasList)
                        Log.e("Freelas", freelasList.size.toString())
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(
                        requireContext(),
                        "Infelizmente, não foi possível fazer a busca",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        }
    }

    private fun setUi(services: List<Services>) {
        with(binding) {
            val serviceMoonCount = services.filter { it.type == "lua" }.size
            val serviceStarCount = services.filter { it.type == "estrela" }.size
            val serviceMeteoroCount = services.filter { it.type == "meteoro" }.size

            itemCountLua.text = serviceMoonCount.toString()
            itemCountEstrela.text = serviceStarCount.toString()
            itemCountMeteoro.text = serviceMeteoroCount.toString()

            Log.e("estrela", services.size.toString())
        }
    }
}
