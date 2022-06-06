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
import com.squareup.picasso.Picasso

class FreelancersAdapter(private val freelancer: List<FreelancersFragment.Freelancer>)
: RecyclerView.Adapter<FreelancersAdapter.FreelancersViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FreelancersViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_freelancers, parent, false)
        return FreelancersViewHolder(view)
    }

    override fun onBindViewHolder(holder: FreelancersViewHolder, position: Int) {
        holder.bind(freelancer[position])
    }

    override fun getItemCount(): Int {
        return freelancer.size
    }

    class FreelancersViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        fun bind(data: FreelancersFragment.Freelancer){
            with(itemView){
                val txtInitial = findViewById<TextView>(R.id.title_name_text)
                val txtEmail = findViewById<TextView>(R.id.email_text)

                txtInitial.text = data.name
                txtEmail.text = data.email
            }
        }
    }

}