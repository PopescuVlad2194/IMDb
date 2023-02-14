package ro.vlad.search_presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ro.vlad.search_domain.model.ResultEntry
import ro.vlad.search_presentation.databinding.ItemSearchResultBinding
import ro.vlad.core.R

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    var onClick: ((ResultEntry) -> Unit)? = null

    inner class SearchViewHolder(val binding: ItemSearchResultBinding) :
            RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<ResultEntry>() {
        override fun areItemsTheSame(oldItem: ResultEntry, newItem: ResultEntry): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ResultEntry, newItem: ResultEntry): Boolean {
            return newItem == oldItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = ItemSearchResultBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val currentItem = differ.currentList[position]
        holder.binding.apply {
            Glide.with(imageSearchItem.context)
                .load(currentItem.image)
                .centerCrop()
                .placeholder(R.drawable.progress_animation)
                .into(imageSearchItem)
            titleSearchItem.text = currentItem.title
            descriptionSearchItem.text = currentItem.description
        }.root.setOnClickListener {
            onClick?.invoke(currentItem)
        }
    }

    override fun getItemCount(): Int = differ.currentList.size
}