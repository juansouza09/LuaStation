package com.example.luastation.tabHome.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.luastation.R
import com.example.luastation.fragments.FavoritosFragment
import com.example.luastation.tabHome.tabs.FreelancersFragment
import com.example.luastation.tabHome.tabs.ServicosFragment
import com.squareup.picasso.Picasso

class ServicosAdapter(private val servico: List<ServicosFragment.Servicos>)
: RecyclerView.Adapter<ServicosAdapter.ServicosViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServicosViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_servicos, parent, false)
        return ServicosViewHolder(view)
    }

    override fun onBindViewHolder(holder: ServicosViewHolder, position: Int) {
        holder.bind(servico[position])
    }

    override fun getItemCount(): Int {
        return servico.size
    }

    class ServicosViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        fun bind(data: ServicosFragment.Servicos){
            with(itemView){
                val txtInitial = findViewById<TextView>(R.id.title_text)
                val txtValor = findViewById<TextView>(R.id.price_text)

                txtInitial.text = data.title
                txtValor.text = data.price
            }
        }
    }

}