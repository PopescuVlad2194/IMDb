package ro.vlad.top_presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ro.vlad.top_domain.model.TopMovie
import ro.vlad.top_presentation.databinding.TopMovieItemBinding
import ro.vlad.core.R

class TopMoviesAdapter : RecyclerView.Adapter<TopMoviesAdapter.MoviesViewHolder>() {

    var onClick: ((TopMovie) -> Unit)? = null

    inner class MoviesViewHolder(val binding: TopMovieItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<TopMovie>() {
        override fun areItemsTheSame(oldItem: TopMovie, newItem: TopMovie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TopMovie, newItem: TopMovie): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val binding = TopMovieItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MoviesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val currentItem = differ.currentList[position]
        holder.binding.apply {
            Glide.with(imageTopMoviesItem.context)
                .load(currentItem.image)
                .centerCrop()
                .placeholder(R.drawable.progress_animation)
                .into(imageTopMoviesItem)
            titleTopMoviesItem.text = currentItem.title
            val rating = if (currentItem.rating.isNotEmpty())
                currentItem.rating.toFloat()
            else 0f
            ratingBar.rating = rating / 2
            rateTopMoviesItem.text = currentItem.rating
            rateVotesTopMoviesItem.text = holder.itemView.context.getString(
                R.string.top_rating_votes,
                currentItem.ratingCount
            )
            releaseDateTopMoviesItem.text = currentItem.year
            rankTopMoviesItem.text = currentItem.rank
        }.root.setOnClickListener {
            onClick?.invoke(currentItem)
        }
    }

    override fun getItemCount(): Int = differ.currentList.size
}