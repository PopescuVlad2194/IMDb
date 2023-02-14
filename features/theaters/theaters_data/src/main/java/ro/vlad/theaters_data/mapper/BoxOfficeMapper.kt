package ro.vlad.theaters_data.mapper

import ro.vlad.core.data.remote.dto.box_office.ItemBoxOffice
import ro.vlad.theaters_domain.model.BoxOfficeMovie

fun ItemBoxOffice.toBoxOfficeMovie(): BoxOfficeMovie {
    return BoxOfficeMovie(
        id = id,
        movieTitle = title.orEmpty(),
        movieImage = image.orEmpty(),
        movieEarningsGross = gross.orEmpty(),
        movieRank = rank.orEmpty(),
        movieWeeks = weeks.orEmpty(),
        movieEarningsWeekend = weekend.orEmpty()
    )
}