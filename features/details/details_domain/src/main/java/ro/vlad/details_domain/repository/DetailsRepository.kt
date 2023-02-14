package ro.vlad.details_domain.repository

import ro.vlad.details_domain.model.MovieInfo

interface DetailsRepository {
    suspend fun getMovieDetails(movieId: String): Result<MovieInfo>
}