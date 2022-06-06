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
import com.example.luastation.tabHome.tabs.MeusFreelasFragment
import com.squareup.picasso.Picasso

class MeusFreelasAdapter(private val freelas: List<MeusFreelasFragment.Freelas>)
: RecyclerView.Adapter<MeusFreelasAdapter.FreelasViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FreelasViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_freelas, parent, false)
        return FreelasViewHolder(view)
    }

    override fun onBindViewHolder(holder: FreelasViewHolder, position: Int) {
        holder.bind(freelas[position])
    }

    override fun getItemCount(): Int {
        return freelas.size
    }

    class FreelasViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        fun bind(data: MeusFreelasFragment.Freelas){
            with(itemView){
                val txtInitial = findViewById<TextView>(R.id.title_text_freela)
                val txtValor = findViewById<TextView>(R.id.price_text_freela)

                txtInitial.text = data.title
                txtValor.text = data.price
            }
        }
    }

}