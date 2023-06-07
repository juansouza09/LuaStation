package br.solutionsjs.luastation.data.repositories

import br.solutionsjs.luastation.data.models.Notification
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class NotificationsRepositoryImpl : NotificationsRepository {
    private val firebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    val firebaseUser = firebaseAuth.currentUser

    private val notificationsPath = "Notification"

    private val database by lazy {
        FirebaseDatabase.getInstance().getReference(notificationsPath)
            .child(firebaseUser!!.uid)
    }

    override fun getNotifications(): List<Notification?> {
        val notificationsList: MutableList<Notification?> = mutableListOf()
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val notificationsArrayList = mutableListOf<Notification?>()
                if (snapshot.exists()) {
                    for (serviceSnapshot in snapshot.children) {
                        val notification = serviceSnapshot.getValue(Notification::class.java)
                        notificationsArrayList.add(notification)
                    }
                    val filteredNotificationsList = notificationsArrayList.filterNotNull()
                    notificationsList.addAll(filteredNotificationsList)
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
        return notificationsList
    }
}
