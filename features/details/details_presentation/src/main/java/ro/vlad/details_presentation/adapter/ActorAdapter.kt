package ro.vlad.details_presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ro.vlad.details_domain.model.Actor
import ro.vlad.details_presentation.databinding.ActorDetailsBinding
import ro.vlad.core.R

class ActorAdapter : RecyclerView.Adapter<ActorAdapter.ActorViewHolder>() {

    inner class ActorViewHolder(val binding: ActorDetailsBinding) :
            RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Actor>() {
        override fun areItemsTheSame(oldItem: Actor, newItem: Actor): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Actor, newItem: Actor): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
        val binding = ActorDetailsBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ActorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        val currentItem = differ.currentList[position]
        holder.binding.apply {
            Glide.with(actorImage)
                .load(currentItem.image)
                .centerCrop()
                .placeholder(R.drawable.progress_animation)
                .into(actorImage)
            actorName.text = currentItem.name
            actorAsCharacter.text = currentItem.asCharacter.ifEmpty { "" }
        }
    }

    override fun getItemCount(): Int = differ.currentList.size
}