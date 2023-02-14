package ro.vlad.top_data.repository

import ro.vlad.core.data.remote.IMDbApi
import ro.vlad.top_data.mapper.toTopMovie
import ro.vlad.top_domain.model.TopMovie
import ro.vlad.top_domain.repository.TopRepository

class TopRepositoryImpl(
    private val api: IMDbApi
) : TopRepository {

    override suspend fun getTopMovies(): Result<List<TopMovie>?> {
        return try {
            val topMoviesDto = api.getTopMovies()
            Result.success(
                topMoviesDto.items?.map { it.toTopMovie() }
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun getTopShows(): Result<List<TopMovie>?> {
        return try {
            val topMoviesDto = api.getTopShows()
            Result.success(
                topMoviesDto.items?.map { it.toTopMovie() }
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}