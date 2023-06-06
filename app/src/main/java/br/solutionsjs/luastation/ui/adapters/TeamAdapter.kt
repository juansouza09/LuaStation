package br.solutionsjs.luastation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.solutionsjs.luastation.databinding.TeamItemBinding
import br.solutionsjs.luastation.data.models.Team
import com.squareup.picasso.Picasso

class TeamAdapter :
    ListAdapter<Team, TeamAdapter.MyViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Team>() {

            override fun areItemsTheSame(oldItem: Team, newItem: Team): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: Team, newItem: Team): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            TeamItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val member = getItem(position)
        val name = member.name
        val job = member.job
        val img = member.imageUrl
        holder.binding.jobText.text = job
        holder.binding.jobText.contentDescription = job
        holder.binding.titleNameText.text = name
        holder.binding.titleNameText.contentDescription = name
        Picasso.get().load(img).into(holder.binding.imgPerson)
    }

    inner class MyViewHolder(val binding: TeamItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}
