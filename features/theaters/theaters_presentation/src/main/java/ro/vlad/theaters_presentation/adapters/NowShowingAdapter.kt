package ro.vlad.theaters_presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ro.vlad.core.R
import ro.vlad.theaters_domain.model.now_showing.NowShowingMovie
import ro.vlad.theaters_presentation.databinding.NowShowingMovieBinding

class NowShowingAdapter : RecyclerView.Adapter<NowShowingAdapter.MoviesViewHolder>() {

    var onClick: ((NowShowingMovie) -> Unit)? = null

    inner class MoviesViewHolder(val binding: NowShowingMovieBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<NowShowingMovie>() {
        override fun areItemsTheSame(oldItem: NowShowingMovie, newItem: NowShowingMovie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NowShowingMovie, newItem: NowShowingMovie): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val binding = NowShowingMovieBinding
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
            movieDetails.text = holder.itemView.context.getString(
                R.string.movie_details,
                currentItem.movieRuntimeMinutes,
                currentItem.movieReleaseDate
            )
        }
    }

    override fun getItemCount(): Int = differ.currentList.size
}