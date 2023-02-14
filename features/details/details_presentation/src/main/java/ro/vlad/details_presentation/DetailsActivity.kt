package ro.vlad.details_presentation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import ro.vlad.core.util.Navigator
import ro.vlad.details_domain.model.MovieInfo
import ro.vlad.details_presentation.adapter.ActorAdapter
import ro.vlad.details_presentation.databinding.ActivityDetailsBinding
import javax.inject.Inject
import ro.vlad.core.R
import ro.vlad.core.util.Constants.DETAILS_EXTRA_KEY
import ro.vlad.core.util.ErrorCode
import ro.vlad.core.util.RequestStateSingleResult

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {
    @Inject
    lateinit var navigatorProvider: Navigator.Provider
    private lateinit var binding: ActivityDetailsBinding
    private lateinit var actorAdapter: ActorAdapter
    private val detailsViewModel: DetailsViewModel by viewModels()

    companion object {
        fun launchActivity(activity: Activity, movieId: String?) {
            val intent = Intent(activity, DetailsActivity::class.java)
            intent.putExtra(DETAILS_EXTRA_KEY, movieId)
            activity.startActivity(intent)
        }
    }

    object NavigateToDetailsActivity : Navigator {
        override fun navigate(activity: Activity, movieId: String?, searchKeywords: String?) {
            launchActivity(activity, movieId)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener { finish() }
        setupActorAdapter()

        val movieId = intent.getStringExtra(DETAILS_EXTRA_KEY)
        movieId?.let {
            detailsViewModel.getMovieDetails(it)
        }

        lifecycleScope.launchWhenStarted {
            detailsViewModel.movieDetails.collect { state ->
                when (state) {
                    is RequestStateSingleResult.Success<*> -> {
                        state.data.let { result ->
                            binding.container.visibility = View.VISIBLE
                            loadMovieDetails(result as MovieInfo)
                        }
                    }
                    is RequestStateSingleResult.Error -> {
                        if (actorAdapter.differ.currentList.isEmpty()) {
                            displayOnScreenError(state)
                        }
                    }
                    else -> {}
                }
            }
        }
    }

    private fun loadMovieDetails(result: MovieInfo) {
        binding.apply {
            Glide.with(movieImage.context)
                .load(result.movieImage)
                .centerCrop()
                .placeholder(R.drawable.progress_animation)
                .into(movieImage)
            movieTitle.text = result.movieTitle
            movieDuration.text = result.movieDuration
            movieState.text = result.movieState
            ratingBar.rating =
                if (result.movieRating.isNotEmpty()) result.movieRating.toFloat() / 2 else 0f
            movieRating.text = result.movieRatingVotes
            moviePlotDescription.text = result.moviePlot
            if (result.movieDirectors.isNotEmpty())
                movieDirectorsDescription.text = result.movieDirectors
            else
                binding.movieDirectorsTitle.visibility = View.GONE
            movieGenresDescription.text = result.movieGenresDescription
            actorAdapter.differ.submitList(result.movieCast)
        }
    }

    private fun setupActorAdapter() {
        actorAdapter = ActorAdapter()
        binding.movieCastRV.apply {
            layoutManager = LinearLayoutManager(
                this@DetailsActivity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = actorAdapter
        }
    }

    private fun displayOnScreenError(state: RequestStateSingleResult.Error) {
        when (state.message) {
            ErrorCode.Network.name -> detailsViewModel.setErrorMessage(
                getString(R.string.network_error)
            )
            ErrorCode.Internet.name -> detailsViewModel.setErrorMessage(
                getString(R.string.internet_error)
            )
            ErrorCode.Unknown.name -> detailsViewModel.setErrorMessage(
                getString(R.string.unknown_error)
            )
        }
    }
}