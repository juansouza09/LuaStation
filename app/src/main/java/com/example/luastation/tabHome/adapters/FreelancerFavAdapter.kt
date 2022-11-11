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
import com.example.luastation.PerfilFreelancerActivity
import com.example.luastation.databinding.ItemFreelancersFavBinding
import com.example.luastation.firebase.models.Freelancers
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FreelancerFavAdapter() :
    ListAdapter<Freelancers, FreelancerFavAdapter.MyViewHolder>(DIFF_CALLBACK) {

    companion object {
        private lateinit var dbRef: DatabaseReference
        private lateinit var firebaseAuth: FirebaseAuth
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Freelancers>() {

            override fun areItemsTheSame(oldItem: Freelancers, newItem: Freelancers): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: Freelancers, newItem: Freelancers): Boolean {
                return oldItem == newItem
            }
        }

        fun desfavoritar(
            id: String?
        ) {
            firebaseAuth = FirebaseAuth.getInstance()
            dbRef = FirebaseDatabase.getInstance().getReference("Users")

            val firebaseUser = firebaseAuth.currentUser

            dbRef.child((firebaseUser!!.uid)).child("FreelancerFav").child(id!!).removeValue()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemFreelancersFavBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val freelancer = getItem(position)
        val id = freelancer.id
        val name = freelancer.name
        val email = freelancer.email
        val cpf_cnpj = freelancer.cpf_cnpj
        val dataNasc = freelancer.dataNasc

        holder.binding.emailText.text = email
        holder.binding.titleNameText.text = name

        holder.binding.icon.setOnClickListener {
            if (!holder.binding.icon.isChecked) {
                desfavoritar(id)
                holder.binding.favoriteAnimation.visibility = View.GONE
                Toast.makeText(
                    holder.itemView.context,
                    "Desfavoritado com sucesso!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        holder.binding.buttonFinalizar.setOnClickListener {
            val context: Context = holder.itemView.context
            val intent = Intent(context, PerfilFreelancerActivity::class.java)
            intent.putExtra("iName", name)
            intent.putExtra("iEmail", email)
            intent.putExtra("iDataNasc", dataNasc)
            intent.putExtra("iCpf_cnpj", cpf_cnpj)
            context.startActivity(intent)
        }
    }

    inner class MyViewHolder(val binding: ItemFreelancersFavBinding) :
        RecyclerView.ViewHolder(binding.root)
}
