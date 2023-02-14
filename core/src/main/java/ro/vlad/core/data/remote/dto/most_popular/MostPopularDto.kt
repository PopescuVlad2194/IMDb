package ro.vlad.core.data.remote.dto.most_popular

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class MostPopularDto(
    @SerializedName("errorMessage")
    val errorMessage: String?,
    @SerializedName("items")
    val items: List<ItemPopular>?
)