package ro.vlad.details_data.mapper

import ro.vlad.core.data.remote.dto.details.DetailsDto
import ro.vlad.details_domain.model.Actor
import ro.vlad.details_domain.model.MovieInfo

fun DetailsDto.toMovieInfo(): MovieInfo {
    val convertedList = mutableListOf<Actor>()
    actorList?.forEach { actor ->
        convertedList.add(
            Actor(
                id = actor.id.orEmpty(),
                asCharacter = actor.asCharacter.orEmpty(),
                image = actor.image.orEmpty(),
                name = actor.name.orEmpty()
            )
        )
    }
    return MovieInfo(
        movieImage = image.orEmpty(),
        movieDuration = runtimeStr.orEmpty(),
        movieState = releaseDate.orEmpty(),
        movieRating = imDbRating.orEmpty(),
        movieRatingVotes = imDbRatingVotes.orEmpty(),
        moviePlot = plot.orEmpty(),
        movieDirectors = directors.orEmpty(),
        movieGenresDescription = genres.orEmpty(),
        movieTitle = fullTitle.orEmpty(),
        movieCast = convertedList
    )
}