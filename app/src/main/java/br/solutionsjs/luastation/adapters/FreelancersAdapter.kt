package br.solutionsjs.luastation.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.solutionsjs.luastation.activities.PerfilFreelancerActivity
import br.solutionsjs.luastation.databinding.ItemFreelancersBinding
import br.solutionsjs.luastation.models.Freelancers
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class FreelancersAdapter :
    ListAdapter<Freelancers, FreelancersAdapter.MyViewHolder>(DIFF_CALLBACK) {

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

        fun favorite(
            id: String?,
            name: String?,
            email: String?,
            dataNasc: String?,
            cpf_cnpj: String?
        ) {
            firebaseAuth = FirebaseAuth.getInstance()
            dbRef = FirebaseDatabase.getInstance().getReference("Users")

            val firebaseUser = firebaseAuth.currentUser
            val freelancer = Freelancers(id, name, email, dataNasc, cpf_cnpj)

            if (firebaseUser != null) {
                if (id != null) {
                    dbRef.child((firebaseUser.uid)).child("FreelancerFav").child(id)
                        .setValue(freelancer)
                }
            }
        }

        fun desfavoritar(
            id: String?
        ) {
            firebaseAuth = FirebaseAuth.getInstance()
            dbRef = FirebaseDatabase.getInstance().getReference("Users")

            val firebaseUser = firebaseAuth.currentUser

            if (firebaseUser != null) {
                if (id != null) {
                    dbRef.child((firebaseUser.uid)).child("FreelancerFav").child(id).removeValue()
                }
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
        val id = freelancer.id
        val name = freelancer.name
        val email = freelancer.email
        val dataNasc = freelancer.dataNasc
        val cpf_cnpj = freelancer.cpf_cnpj

        holder.binding.emailText.text = email
        holder.binding.titleNameText.text = name
        holder.binding.emailText.contentDescription = email
        holder.binding.titleNameText.contentDescription = name

        holder.binding.icon.setOnClickListener {
            if (holder.binding.icon.isChecked) {
                holder.binding.favoriteAnimation.visibility = View.VISIBLE
                favorite(id, name, email, cpf_cnpj, dataNasc)
                Toast.makeText(
                    holder.itemView.context,
                    "Favoritado com sucesso!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                desfavoritar(id)
                Toast.makeText(
                    holder.itemView.context,
                    "Desfavoritado com sucesso!",
                    Toast.LENGTH_SHORT
                ).show()
                holder.binding.favoriteAnimation.visibility = View.GONE
            }
        }

        holder.binding.buttonFinalizar.setOnClickListener {
            val context: Context = holder.itemView.context
            val intent = Intent(context, PerfilFreelancerActivity::class.java)
            intent.putExtra("iName", name)
            intent.putExtra("iEmail", email)
            intent.putExtra("iDataNasc", dataNasc)
            intent.putExtra("iCpf_cnpj", cpf_cnpj)
            intent.putExtra("iFreelancerID", id)
            context.startActivity(intent)
        }
    }

    inner class MyViewHolder(val binding: ItemFreelancersBinding) :
        RecyclerView.ViewHolder(binding.root)
}
