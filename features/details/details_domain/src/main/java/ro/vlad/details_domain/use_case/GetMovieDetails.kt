package ro.vlad.details_domain.use_case

import ro.vlad.details_domain.model.MovieInfo
import ro.vlad.details_domain.repository.DetailsRepository

class GetMovieDetails(
    private val repository: DetailsRepository
) {
    suspend operator fun invoke(movieId: String): Result<MovieInfo?> {
        return repository.getMovieDetails(movieId)
    }
}