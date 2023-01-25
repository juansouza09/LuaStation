package com.example.luastation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.luastation.databinding.FragmentNotificacoesBinding
import com.example.luastation.models.Notification
import com.example.luastation.adapters.NotificationAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DatabaseError

class NotificacaoFragment : Fragment() {

    private var _binding: FragmentNotificacoesBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerview: RecyclerView

    private val database by lazy {
        FirebaseDatabase
            .getInstance()
            .getReference("Notification")
            .child(firebaseAuth.currentUser!!.uid)
    }
    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }
    private lateinit var myAdapter: NotificationAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificacoesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        getNotificationData()
    }

    private fun setupUi() {
        binding.recyclerServicos.layoutManager =
            LinearLayoutManager(this.requireContext(), RecyclerView.VERTICAL, false)
        binding.recyclerServicos.adapter = myAdapter
    }

    private fun getNotificationData() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val notificationsArrayList = mutableListOf<Notification>()
                if (snapshot.exists()) {
                    for (serviceSnapshot in snapshot.children) {
                        val notification = serviceSnapshot.getValue(Notification::class.java)
                        notificationsArrayList.add(notification!!)
                    }
                    myAdapter.submitList(notificationsArrayList)
                    recyclerview.visibility = View.VISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}
