package ro.vlad.popular_presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ro.vlad.popular_domain.model.PopularMovie
import ro.vlad.popular_presentation.databinding.PopularMovieItemBinding
import ro.vlad.core.R

class PopularMoviesAdapter : RecyclerView.Adapter<PopularMoviesAdapter.MoviesViewHolder>() {

    var onClick: ((PopularMovie) -> Unit)? = null

    inner class MoviesViewHolder(val binding: PopularMovieItemBinding) :
            RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<PopularMovie>() {
        override fun areItemsTheSame(oldItem: PopularMovie, newItem: PopularMovie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PopularMovie, newItem: PopularMovie): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val binding = PopularMovieItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MoviesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val currentItem = differ.currentList[position]
        holder.binding.apply {
            Glide.with(imagePopularMoviesItem.context)
                .load(currentItem.image)
                .centerCrop()
                .placeholder(R.drawable.progress_animation)
                .into(imagePopularMoviesItem)
            titlePopularMoviesItem.text = currentItem.title
            val rating = if (currentItem.rating.isNotEmpty())
                currentItem.rating.toFloat()
            else 0f
            ratingBar.rating = rating / 2
            ratePopularMoviesItem.text = currentItem.rating
            rankUpDownPopularMoviesItem.text = currentItem.rankUpDown
            rateVotesPopularMoviesItem.text = holder.itemView.context.getString(
                R.string.top_rating_votes,
                currentItem.ratingCount
            )
            releaseDatePopularMoviesItem.text = currentItem.year
            rankPopularMoviesItem.text = currentItem.rank
        }.root.setOnClickListener {
            onClick?.invoke(currentItem)
        }
    }

    override fun getItemCount(): Int = differ.currentList.size
}