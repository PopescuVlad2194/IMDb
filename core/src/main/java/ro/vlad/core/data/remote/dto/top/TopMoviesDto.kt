package ro.vlad.core.data.remote.dto.top

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class TopMoviesDto(
    @SerializedName("errorMessage")
    val errorMessage: String?,
    @SerializedName("items")
    val items: List<ItemTop>?
)