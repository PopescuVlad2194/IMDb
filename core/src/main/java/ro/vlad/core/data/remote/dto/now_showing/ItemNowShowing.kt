package ro.vlad.core.data.remote.dto.now_showing

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ItemNowShowing(
    @SerializedName("contentRating")
    val contentRating: String?,
    @SerializedName("directorList")
    val directorList: List<Director>?,
    @SerializedName("directors")
    val directors: String?,
    @SerializedName("fullTitle")
    val fullTitle: String?,
    @SerializedName("genreList")
    val genreList: List<Genre>?,
    @SerializedName("genres")
    val genres: String?,
    @SerializedName("id")
    val id: String,
    @SerializedName("imDbRating")
    val imDbRating: String?,
    @SerializedName("imDbRatingCount")
    val imDbRatingCount: String?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("metacriticRating")
    val metacriticRating: String?,
    @SerializedName("plot")
    val plot: String?,
    @SerializedName("releaseState")
    val releaseState: String?,
    @SerializedName("runtimeMins")
    val runtimeMins: String?,
    @SerializedName("runtimeStr")
    val runtimeStr: String?,
    @SerializedName("starList")
    val starList: List<Star>?,
    @SerializedName("stars")
    val stars: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("year")
    val year: String?
)