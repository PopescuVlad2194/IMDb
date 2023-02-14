package ro.vlad.popular_domain.repository

import ro.vlad.popular_domain.model.PopularMovie

interface PopularRepository {

    suspend fun getPopularMovies(): Result<List<PopularMovie>?>

    suspend fun getPopularShows(): Result<List<PopularMovie>?>

}