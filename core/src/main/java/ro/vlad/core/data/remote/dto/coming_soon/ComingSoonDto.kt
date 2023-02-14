package ro.vlad.core.data.remote.dto.coming_soon

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ComingSoonDto(
    @SerializedName("errorMessage")
    val errorMessage: String?,
    @SerializedName("items")
    val items: List<ItemComingSoon>?
)