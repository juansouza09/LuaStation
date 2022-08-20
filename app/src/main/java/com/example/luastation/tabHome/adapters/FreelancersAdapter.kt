package com.example.luastation.tabHome.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.luastation.databinding.ItemFreelancersBinding
import com.example.luastation.firebase.models.Freelancers

class FreelancersAdapter(
    val click: () -> Unit
) : ListAdapter<Freelancers, FreelancersAdapter.MyViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Freelancers>() {
            override fun areItemsTheSame(oldItem: Freelancers, newItem: Freelancers): Boolean {
                return oldItem.email == newItem.email
            }

            override fun areContentsTheSame(oldItem: Freelancers, newItem: Freelancers): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemFreelancersBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val freelancer = getItem(position)
        holder.binding.titleNameText.text = freelancer.name
        holder.binding.emailText.text = freelancer.email
        holder.binding.icon.setOnClickListener {
            holder.binding.favoriteAnimation.visibility = View.VISIBLE
            if (!holder.binding.icon.isChecked) {
                holder.binding.favoriteAnimation.visibility = View.GONE
            }
        }
        holder.binding.buttonFinalizar.setOnClickListener { click() }
    }

    inner class MyViewHolder(val binding: ItemFreelancersBinding) :
        RecyclerView.ViewHolder(binding.root)
}
