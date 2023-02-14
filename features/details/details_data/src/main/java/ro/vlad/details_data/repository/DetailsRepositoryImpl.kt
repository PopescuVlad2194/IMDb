package ro.vlad.details_data.repository

import ro.vlad.core.data.remote.IMDbApi
import ro.vlad.details_data.mapper.toMovieInfo
import ro.vlad.details_domain.model.MovieInfo
import ro.vlad.details_domain.repository.DetailsRepository

class DetailsRepositoryImpl(
    private val api: IMDbApi
): DetailsRepository {
    override suspend fun getMovieDetails(movieId: String): Result<MovieInfo> {
        return try {
            val dto = api.getMovieDetails(movieId = movieId)
            Result.success(
                dto.toMovieInfo()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}