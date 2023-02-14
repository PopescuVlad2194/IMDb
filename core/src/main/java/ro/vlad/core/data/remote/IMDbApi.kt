package ro.vlad.core.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import ro.vlad.core.data.remote.dto.box_office.BoxOfficeDto
import ro.vlad.core.data.remote.dto.coming_soon.ComingSoonDto
import ro.vlad.core.data.remote.dto.details.DetailsDto
import ro.vlad.core.data.remote.dto.most_popular.MostPopularDto
import ro.vlad.core.data.remote.dto.now_showing.NowShowingDto
import ro.vlad.core.data.remote.dto.search.SearchDto
import ro.vlad.core.data.remote.dto.top.TopMoviesDto
import ro.vlad.core.util.Constants

interface IMDbApi {

    @GET("en/API/InTheaters/{apiKey}")
    suspend fun getNowShowing(
        @Path("apiKey")
        apiKey: String = Constants.API_KEY
    ): NowShowingDto

    @GET("en/API/ComingSoon/{apiKey}")
    suspend fun getComingSoon(
        @Path("apiKey")
        apiKey: String = Constants.API_KEY
    ): ComingSoonDto

    @GET("en/API/BoxOffice/{apiKey}")
    suspend fun getBoxOffice(
        @Path("apiKey")
        apiKey: String = Constants.API_KEY
    ): BoxOfficeDto

    @GET("en/API/Top250Movies/{apiKey}")
    suspend fun getTopMovies(
        @Path("apiKey")
        apiKey: String = Constants.API_KEY
    ): TopMoviesDto

    @GET("en/API/Top250TVs/{apiKey}")
    suspend fun getTopShows(
        @Path("apiKey")
        apiKey: String = Constants.API_KEY
    ): TopMoviesDto

    @GET("en/API/MostPopularMovies/{apiKey}")
    suspend fun getPopularMovies(
        @Path("apiKey")
        apiKey: String = Constants.API_KEY
    ): MostPopularDto

    @GET("en/API/MostPopularTVs/{apiKey}")
    suspend fun getPopularShows(
        @Path("apiKey")
        apiKey: String = Constants.API_KEY
    ): MostPopularDto

    @GET("en/API/Search/{apiKey}/{expression}")
    suspend fun getSearchResult(
        @Path("apiKey")
        apiKey: String = Constants.API_KEY,
        @Path("expression")
        expression: String
    ): SearchDto

    @GET("en/API/Title/{apiKey}/{movieId}/FullActor")
    suspend fun getMovieDetails(
        @Path("apiKey")
        apiKey: String = Constants.API_KEY,
        @Path("movieId")
        movieId: String
    ): DetailsDto

}