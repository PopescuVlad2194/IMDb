package ro.vlad.popular_domain.model

data class PopularMovie(
    val id: String,
    val crew: String,
    val title: String,
    val rating: String,
    val ratingCount: String,
    val image: String,
    val rank: String,
    val rankUpDown: String,
    val year: String
)