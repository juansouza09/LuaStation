package com.example.luastation.tabHome.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.luastation.R
import com.example.luastation.fragments.NotificacaoFragment

class NotificationAdapter(private val notification: List<NotificacaoFragment.Notification>) :
    RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_notificacao, parent, false)
        return NotificationViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.bind(notification[position])
    }

    override fun getItemCount(): Int {
        return notification.size
    }

    class NotificationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(data: NotificacaoFragment.Notification) {
            with(itemView) {
                val txtInitial = findViewById<TextView>(R.id.title_notification_text)

                txtInitial.text = data.description
            }
        }
    }
}
