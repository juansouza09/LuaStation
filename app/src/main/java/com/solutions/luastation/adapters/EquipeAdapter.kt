package com.solutions.luastation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.solutionsjs.luastation.databinding.ItemEquipeBinding
import com.solutions.luastation.models.Equipe
import com.squareup.picasso.Picasso

class EquipeAdapter :
    ListAdapter<Equipe, EquipeAdapter.MyViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Equipe>() {

            override fun areItemsTheSame(oldItem: Equipe, newItem: Equipe): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: Equipe, newItem: Equipe): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemEquipeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val member = getItem(position)
        val name = member.name
        val job = member.job
        val img = member.imageUrl
        holder.binding.jobText.text = job
        holder.binding.titleNameText.text = name
        Picasso.get().load(img).into(holder.binding.imgPerson)


    }

    inner class MyViewHolder(val binding: ItemEquipeBinding) :
        RecyclerView.ViewHolder(binding.root)
}
