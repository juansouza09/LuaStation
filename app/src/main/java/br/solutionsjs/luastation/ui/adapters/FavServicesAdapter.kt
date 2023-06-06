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

class FavServicesAdapter : ListAdapter<Services, FavServicesAdapter.MyViewHolder>(DIFF_CALLBACK) {

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
        holder.binding.titleText.contentDescription = service.name
        holder.binding.priceText.contentDescription = service.price
        holder.binding.timeText.contentDescription = service.days
        holder.binding.plataformaText.contentDescription = service.plataform
        holder.binding.descriptionText.contentDescription = service.desc

        holder.binding.icon.let {
            it.setOnClickListener {
                holder.binding.icon.isChecked = false
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
        }

        holder.itemView.let {
            it.setOnClickListener {
                val context: Context = holder.itemView.context
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

    inner class MyViewHolder(val binding: ServiceItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}
