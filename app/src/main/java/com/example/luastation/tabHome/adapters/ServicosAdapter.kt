package com.example.luastation.tabHome.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.luastation.databinding.ItemServicosBinding
import com.example.luastation.firebase.models.Services
import com.squareup.picasso.Picasso

class ServicosAdapter(
    val click: () -> Unit
) : ListAdapter<Services, ServicosAdapter.MyViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Services>() {
            override fun areItemsTheSame(oldItem: Services, newItem: Services): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Services, newItem: Services): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemServicosBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val service = getItem(position)

        Picasso.get().load(service.img).into(holder.binding.imgDificuldade)
        holder.binding.titleText.text = service.name
        holder.binding.priceText.text = service.price
        holder.binding.timeText.text = service.days
        holder.binding.plataformaText.text = service.plataform
        holder.binding.descriptionText.text = service.desc

        holder.binding.icon.setOnClickListener {
            holder.binding.favoriteAnimation.visibility = View.VISIBLE
            if (!holder.binding.icon.isChecked) {
                holder.binding.favoriteAnimation.visibility = View.GONE
            }
        }

        holder.itemView.setOnClickListener { click() }
    }

    inner class MyViewHolder(val binding: ItemServicosBinding) :
        RecyclerView.ViewHolder(binding.root)
}
