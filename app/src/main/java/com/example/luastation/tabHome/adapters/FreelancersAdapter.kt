package com.example.luastation.tabHome.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.luastation.firebase.models.Freelancers
import com.example.luastation.R

class FreelancersAdapter(private val freelancer: List<Freelancers>) :
    RecyclerView.Adapter<FreelancersAdapter.FreelancersViewHolder>() {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick()
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FreelancersViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_freelancers, parent, false)
        return FreelancersViewHolder(view, mListener)
    }

    override fun onBindViewHolder(holder: FreelancersViewHolder, position: Int) {
        holder.bind(freelancer[position])
    }

    override fun getItemCount(): Int {
        return freelancer.size
    }

    class FreelancersViewHolder(itemView: View, listener: onItemClickListener) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(data: Freelancers) {
            with(itemView) {
                val txtInitial = findViewById<TextView>(R.id.title_name_text)
                val txtEmail = findViewById<TextView>(R.id.email_text)

                txtInitial.text = data.name
                txtEmail.text = data.email
            }
        }

        init {
            itemView.setOnClickListener {
                listener.onItemClick()
            }
        }
    }
}
