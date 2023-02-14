package ro.vlad.top_domain.repository

import ro.vlad.top_domain.model.TopMovie

interface TopRepository {

    suspend fun getTopMovies(): Result<List<TopMovie>?>

    suspend fun getTopShows(): Result<List<TopMovie>?>

}