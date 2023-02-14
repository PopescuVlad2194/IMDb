package ro.vlad.theaters_domain.repository

import ro.vlad.theaters_domain.model.BoxOfficeMovie
import ro.vlad.theaters_domain.model.ComingSoonMovie
import ro.vlad.theaters_domain.model.MovieInfo
import ro.vlad.theaters_domain.model.now_showing.NowShowingMovie

interface TheatersRepository {

    suspend fun getNowShowingMovies(): Result<List<NowShowingMovie>?>

    suspend fun getBoxOfficeMovies(): Result<List<BoxOfficeMovie>?>

    suspend fun getComingSoonMovies(): Result<List<ComingSoonMovie>?>

}