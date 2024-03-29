package br.solutionsjs.luastation.ui.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.solutionsjs.luastation.ui.activities.ServiceDetailsActivity
import br.solutionsjs.luastation.databinding.ServiceItemBinding
import br.solutionsjs.luastation.data.models.Services
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

class ServicesAdapter : ListAdapter<Services, ServicesAdapter.MyViewHolder>(DIFF_CALLBACK) {

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

            if (firebaseUser != null) {
                if (id != null) {
                    dbRef.child((firebaseUser.uid)).child("ServicosFav").child(id).setValue(servico)
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
                    dbRef.child((firebaseUser.uid)).child("ServicosFav").child(id).removeValue()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ServiceItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val service = getItem(position)
        holder.bind(service)
    }

    inner class MyViewHolder(val binding: ServiceItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(service: Services) {
            val id = service.id
            val name = service.name
            val price = service.price
            val days = service.days
            val plataform = service.plataform
            val desc = service.desc
            val img = service.img
            val creator = service.creator

            Picasso.get().load(service.img).into(binding.imgDificuldade)
            binding.titleText.text = service.name
            binding.priceText.text = service.price
            binding.timeText.text = service.days
            binding.plataformaText.text = service.plataform
            binding.descriptionText.text = service.desc
            binding.titleText.contentDescription = service.name
            binding.priceText.contentDescription = service.price
            binding.timeText.contentDescription = service.days
            binding.plataformaText.contentDescription = service.plataform
            binding.descriptionText.contentDescription = service.desc

            binding.icon.let {
                it.setOnClickListener {
                    if (binding.icon.isChecked) {
                        binding.favoriteAnimation.visibility = View.VISIBLE
                        favorite(id, name, img, price, days, desc, plataform, creator)
                        Toast.makeText(
                            itemView.context,
                            "Favoritado com sucesso!",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        desfavoritar(id)
                        Toast.makeText(
                            itemView.context,
                            "Desfavoritado com sucesso!",
                            Toast.LENGTH_SHORT
                        ).show()
                        binding.favoriteAnimation.visibility = View.GONE
                    }
                }
            }

            itemView.let {
                it.setOnClickListener {
                    val context: Context = itemView.context
                    val intent = Intent(context, ServiceDetailsActivity::class.java)
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
        }
    }
}
