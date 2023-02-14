package ro.vlad.popular_data.mapper

import ro.vlad.core.data.remote.dto.most_popular.ItemPopular
import ro.vlad.popular_domain.model.PopularMovie

fun ItemPopular.toPopularMovie(): PopularMovie {
    return PopularMovie(
        id = id,
        crew = crew.orEmpty(),
        title = title.orEmpty(),
        rating = imDbRating.orEmpty(),
        ratingCount = imDbRatingCount.orEmpty(),
        image = image.orEmpty(),
        rank = rank.orEmpty(),
        rankUpDown = rankUpDown.orEmpty(),
        year = year.orEmpty()
    )
}