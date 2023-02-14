package ro.vlad.theaters_data.mapper

import ro.vlad.core.data.remote.dto.now_showing.ItemNowShowing
import ro.vlad.theaters_domain.model.now_showing.NowShowingMovie

fun ItemNowShowing.toNowShowingMovie(): NowShowingMovie {
    return NowShowingMovie(
        id = id,
        movieTitle = fullTitle.orEmpty(),
        movieImage = image.orEmpty(),
        movieRuntimeMinutes = runtimeMins.orEmpty(),
        movieReleaseDate = releaseState.orEmpty()
    )
}