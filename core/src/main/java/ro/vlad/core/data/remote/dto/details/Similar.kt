package ro.vlad.core.data.remote.dto.details

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Similar(
    @SerializedName("directors")
    val directors: String?,
    @SerializedName("fullTitle")
    val fullTitle: String?,
    @SerializedName("genres")
    val genres: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("imDbRating")
    val imDbRating: String?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("plot")
    val plot: String?,
    @SerializedName("stars")
    val stars: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("year")
    val year: String?
)