package ro.vlad.theaters_data.mapper

import ro.vlad.core.data.remote.dto.coming_soon.ItemComingSoon
import ro.vlad.theaters_domain.model.ComingSoonMovie

fun ItemComingSoon.toComingSoonMovie(): ComingSoonMovie {
    return ComingSoonMovie(
        id = id.orEmpty(),
        movieImage = image.orEmpty(),
        movieTitle = fullTitle.orEmpty(),
        movieRuntimeMinutes = runtimeMins.orEmpty(),
        movieReleaseDate = releaseState.orEmpty()
    )
}