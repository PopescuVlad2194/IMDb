package ro.vlad.popular_data.repository

import ro.vlad.core.data.remote.IMDbApi
import ro.vlad.popular_data.mapper.toPopularMovie
import ro.vlad.popular_domain.model.PopularMovie
import ro.vlad.popular_domain.repository.PopularRepository

class PopularRepositoryImpl(
    private val api: IMDbApi
) : PopularRepository {

    override suspend fun getPopularMovies(): Result<List<PopularMovie>?> {
        return try {
            val popularMoviesDto = api.getPopularMovies()
            Result.success(
                popularMoviesDto.items?.map { it.toPopularMovie() }
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun getPopularShows(): Result<List<PopularMovie>?> {
        return try {
            val popularShowsDto = api.getPopularShows()
            Result.success(
                popularShowsDto.items?.map { it.toPopularMovie() }
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}