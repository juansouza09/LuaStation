package com.example.luastation.tabHome.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.luastation.DetalhesActivity
import com.example.luastation.databinding.ItemServicosBinding
import com.example.luastation.firebase.models.Services
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

class ServicosAdapter() : ListAdapter<Services, ServicosAdapter.MyViewHolder>(DIFF_CALLBACK) {

    companion object {
        private lateinit var dbRef: DatabaseReference
        private lateinit var firebaseAuth: FirebaseAuth
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Services>() {
            override fun areItemsTheSame(oldItem: Services, newItem: Services): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Services, newItem: Services): Boolean {
                return oldItem == newItem
            }
        }

        fun favorite(
            id: String?,
            name: String?,
            img: String?,
            price: String?,
            days: String?,
            desc: String?,
            plataform: String?,
            creator: String?
        ) {
            firebaseAuth = FirebaseAuth.getInstance()
            dbRef = FirebaseDatabase.getInstance().getReference("Users")

            val firebaseUser = firebaseAuth.currentUser
            val servico = Services(id, name, img, price, days, desc, plataform, creator)

            dbRef.child((firebaseUser!!.uid)).child("ServicosFav").child(id!!).setValue(servico)
        }

        fun desfavoritar(
            id: String?
        ) {
            firebaseAuth = FirebaseAuth.getInstance()
            dbRef = FirebaseDatabase.getInstance().getReference("Users")

            val firebaseUser = firebaseAuth.currentUser

            dbRef.child((firebaseUser!!.uid)).child("ServicosFav").child(id!!).removeValue()
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

        val id = service.id
        val name = service.name
        val price = service.price
        val days = service.days
        val plataform = service.plataform
        val desc = service.desc
        val img = service.img
        val creator = service.creator

        Picasso.get().load(service.img).into(holder.binding.imgDificuldade)
        holder.binding.titleText.text = service.name
        holder.binding.priceText.text = service.price
        holder.binding.timeText.text = service.days
        holder.binding.plataformaText.text = service.plataform
        holder.binding.descriptionText.text = service.desc

        holder.binding.icon.setOnClickListener {
            if (holder.binding.icon.isChecked) {
                holder.binding.favoriteAnimation.visibility = View.VISIBLE
                favorite(id, name, img, price, days, desc, plataform, creator)
                Toast.makeText(holder.itemView.context, "Favoritado com sucesso!", Toast.LENGTH_SHORT).show()
            } else {
                desfavoritar(id)
                Toast.makeText(holder.itemView.context, "Desfavoritado com sucesso!", Toast.LENGTH_SHORT).show()
                holder.binding.favoriteAnimation.visibility = View.GONE
            }
        }

        holder.itemView.setOnClickListener {
            val context: Context = holder.itemView.context
            val intent = Intent(context, DetalhesActivity::class.java)
            intent.putExtra("iId", id)
            intent.putExtra("iTitle", name)
            intent.putExtra("iPrice", price)
            intent.putExtra("iDays", days)
            intent.putExtra("iPlataform", plataform)
            intent.putExtra("iDesc", desc)
            intent.putExtra("iImg", img)
            intent.putExtra("iCreator", creator)
            context.startActivity(intent)
        }
    }

    inner class MyViewHolder(val binding: ItemServicosBinding) :
        RecyclerView.ViewHolder(binding.root)
}
