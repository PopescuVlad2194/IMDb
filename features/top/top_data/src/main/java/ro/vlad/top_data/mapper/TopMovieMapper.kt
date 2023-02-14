package ro.vlad.top_data.mapper

import ro.vlad.core.data.remote.dto.top.ItemTop
import ro.vlad.top_domain.model.TopMovie

fun ItemTop.toTopMovie(): TopMovie {
    return TopMovie(
        id = id,
        crew = crew.orEmpty(),
        title = fullTitle.orEmpty(),
        rating = imDbRating.orEmpty(),
        ratingCount = imDbRatingCount.orEmpty(),
        image = image.orEmpty(),
        rank = rank.orEmpty(),
        year = year.orEmpty()
    )
}