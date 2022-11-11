package com.example.luastation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.luastation.databinding.FragmentNotificacoesBinding
import com.example.luastation.firebase.models.Notification
import com.example.luastation.firebase.models.Services
import com.example.luastation.tabHome.adapters.NotificationAdapter
import com.example.luastation.tabHome.adapters.ServicosAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class NotificacaoFragment : Fragment() {
    private lateinit var binding: FragmentNotificacoesBinding
    private lateinit var recyclerview: RecyclerView
    private lateinit var database: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var myAdapter: NotificationAdapter
    var layoutManager: LinearLayoutManager? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotificacoesBinding.inflate(inflater, container, false)
        setRecyclerView()
        firebaseAuth = FirebaseAuth.getInstance()
        getNotificationData()
        return binding.root
    }

    private fun setRecyclerView() {
        recyclerview = binding.recyclerServicos
        recyclerview.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this.requireContext(), RecyclerView.VERTICAL, false)
        recyclerview.layoutManager = layoutManager
        myAdapter = NotificationAdapter()
        recyclerview.adapter = myAdapter
    }

    fun getNotificationData() {
        database = FirebaseDatabase.getInstance().getReference("Notification")
            .child(firebaseAuth.currentUser!!.uid)
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
