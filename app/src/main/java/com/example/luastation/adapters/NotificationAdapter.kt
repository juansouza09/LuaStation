package com.example.luastation.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.luastation.R
import com.example.luastation.activities.NotificationActivity
import com.example.luastation.databinding.ItemNotificacaoBinding
import com.example.luastation.models.Notification

class NotificationAdapter :
    ListAdapter<Notification, NotificationAdapter.MyViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Notification>() {
            override fun areItemsTheSame(oldItem: Notification, newItem: Notification): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Notification, newItem: Notification): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemNotificacaoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val notification = getItem(position)

        val id = notification.id
        val title = notification.title
        val desc = notification.description
        val email = notification.email
        val isActive = notification.isActive

        if (isActive != true) {
            holder.binding.itemContainer.setImageResource(R.drawable.dark_purple_shape)
        } else {
            holder.binding.itemContainer.setImageResource(R.drawable.purple_shape)
        }

        holder.binding.titleNotificationText.text = notification.title

        holder.itemView.let {
            it.setOnClickListener {
                val context: Context = holder.itemView.context
                val intent = Intent(context, NotificationActivity::class.java)
                intent.putExtra("iId", id)
                intent.putExtra("iTitle", title)
                intent.putExtra("iDesc", desc)
                intent.putExtra("iEmail", email)
                context.startActivity(intent)
            }
        }
    }

    inner class MyViewHolder(val binding: ItemNotificacaoBinding) :
        RecyclerView.ViewHolder(binding.root)
}
