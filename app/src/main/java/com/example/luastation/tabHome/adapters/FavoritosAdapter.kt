package com.example.luastation.tabHome.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.luastation.R
import com.example.luastation.fragments.FavoritosFragment

class FavoritosAdapter(private val favorito: List<FavoritosFragment.Favoritos>) :
    RecyclerView.Adapter<FavoritosAdapter.FavoritosViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritosViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_favoritos, parent, false)
        return FavoritosViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoritosViewHolder, position: Int) {
        holder.bind(favorito[position])
    }

    override fun getItemCount(): Int {
        return favorito.size
    }

    class FavoritosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(data: FavoritosFragment.Favoritos) {
            with(itemView) {
                val txtInitial = findViewById<TextView>(R.id.title_fav_text)
                val txtValor = findViewById<TextView>(R.id.price_fav_text)

                txtInitial.text = data.title
                txtValor.text = data.price
            }
        }
    }
}
