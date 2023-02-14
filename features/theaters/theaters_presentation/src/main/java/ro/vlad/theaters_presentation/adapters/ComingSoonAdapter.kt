package ro.vlad.theaters_presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ro.vlad.theaters_domain.model.ComingSoonMovie
import ro.vlad.theaters_presentation.databinding.ComingSoonMovieBinding
import ro.vlad.core.R

class ComingSoonAdapter : RecyclerView.Adapter<ComingSoonAdapter.MoviesViewHolder>() {

    var onClick: ((ComingSoonMovie) -> Unit)? = null

    inner class MoviesViewHolder(val binding: ComingSoonMovieBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<ComingSoonMovie>() {
        override fun areItemsTheSame(oldItem: ComingSoonMovie, newItem: ComingSoonMovie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ComingSoonMovie,
            newItem: ComingSoonMovie
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val binding = ComingSoonMovieBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MoviesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val currentItem = differ.currentList[position]
        holder.binding.apply {
            Glide.with(movieImg.context)
                .load(currentItem.movieImage)
                .centerCrop()
                .placeholder(R.drawable.progress_animation)
                .into(movieImg)
            movieImg.setOnClickListener {
                onClick?.invoke(currentItem)
            }
            movieTitle.text = currentItem.movieTitle
            if (currentItem.movieRuntimeMinutes.isEmpty())
                movieDetails.text = currentItem.movieReleaseDate
            else
                movieDetails.text = holder.itemView.context.getString(
                    R.string.movie_details,
                    currentItem.movieRuntimeMinutes,
                    currentItem.movieReleaseDate
                )
        }.root.setOnClickListener {
            onClick?.invoke(currentItem)
        }
    }

    override fun getItemCount(): Int = differ.currentList.size
}