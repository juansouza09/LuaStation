package com.example.luastation.tabHome.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.luastation.R
import com.example.luastation.firebase.models.Services

class ServicosAdapter(private val serviceList: ArrayList<Services>) :
    RecyclerView.Adapter<ServicosAdapter.ServicosViewHolder>() {
    inner class ServicosViewHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {

        var name: TextView
        var price: TextView
        var plataform: TextView
        var days: TextView


        init {
            name = itemView.findViewById<TextView>(R.id.title_text)
            price = itemView.findViewById<TextView>(R.id.price_text)
            plataform = itemView.findViewById<TextView>(R.id.plataforma_text)
            days = itemView.findViewById<TextView>(R.id.time_text)
            itemView.setOnClickListener {
                listener.onItemClick()
            }
        }
    }

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick()
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServicosViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_servicos, parent, false)
        return ServicosViewHolder(view, mListener)
    }

    override fun onBindViewHolder(holder: ServicosViewHolder, position: Int) {
        holder.name.text = serviceList[position].name
        holder.price.text = serviceList[position].price
        holder.days.text = serviceList[position].days
        holder.plataform.text = serviceList[position].plataform
    }

    override fun getItemCount(): Int {
        return serviceList.size
    }
}
