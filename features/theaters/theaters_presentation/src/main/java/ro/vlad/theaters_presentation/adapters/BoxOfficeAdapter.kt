package ro.vlad.theaters_presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ro.vlad.theaters_domain.model.BoxOfficeMovie
import ro.vlad.theaters_presentation.databinding.BoxOfficeMovieBinding
import ro.vlad.core.R

class BoxOfficeAdapter : RecyclerView.Adapter<BoxOfficeAdapter.MoviesViewHolder>() {

    var onClick: ((BoxOfficeMovie) -> Unit)? = null

    inner class MoviesViewHolder(val binding: BoxOfficeMovieBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<BoxOfficeMovie>() {
        override fun areItemsTheSame(oldItem: BoxOfficeMovie, newItem: BoxOfficeMovie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: BoxOfficeMovie, newItem: BoxOfficeMovie): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val binding = BoxOfficeMovieBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MoviesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val currentItem = differ.currentList[position]
        holder.binding.apply {
            Glide.with(imageBoxOfficeItem.context)
                .load(currentItem.movieImage)
                .centerCrop()
                .placeholder(R.drawable.progress_animation)
                .into(imageBoxOfficeItem)
            titleBoxOfficeItem.text = currentItem.movieTitle
            weekendBoxOfficeItem.text = holder.itemView.context.getString(
                R.string.box_office_weekend,
                currentItem.movieEarningsWeekend
            )
            grossBoxOfficeItem.text = holder.itemView.context.getString(
                R.string.box_office_gross,
                currentItem.movieEarningsGross
            )
            weeksBoxOfficeItem.text = holder.itemView.context.getString(
                R.string.box_office_week,
                currentItem.movieWeeks
            )
            rankBoxOffice.text = currentItem.movieRank
        }.root.setOnClickListener {
            onClick?.invoke(currentItem)
        }
    }

    override fun getItemCount(): Int = differ.currentList.size
}