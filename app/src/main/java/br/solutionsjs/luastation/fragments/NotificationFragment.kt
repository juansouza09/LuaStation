package br.solutionsjs.luastation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.solutionsjs.luastation.adapters.NotificationAdapter
import br.solutionsjs.luastation.databinding.FragmentNotificationsBinding
import br.solutionsjs.luastation.models.Notification
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class NotificationFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!

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
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myAdapter = NotificationAdapter()
        setupUi()
        lifecycleScope.launch { getNotificationData() }
    }

    private fun setupUi() {
        binding.recyclerServicos.layoutManager =
            LinearLayoutManager(this.requireContext(), RecyclerView.VERTICAL, false)
        binding.recyclerServicos.adapter = myAdapter
    }

    private suspend fun getNotificationData() = coroutineScope {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val notificationsArrayList = mutableListOf<Notification?>()
                if (snapshot.exists()) {
                    for (serviceSnapshot in snapshot.children) {
                        val notification = serviceSnapshot.getValue(Notification::class.java)
                        notificationsArrayList.add(notification)
                    }
                    myAdapter.submitList(notificationsArrayList)
                    binding.recyclerServicos.isVisible = true
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}
