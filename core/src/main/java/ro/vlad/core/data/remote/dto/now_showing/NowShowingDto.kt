package ro.vlad.core.data.remote.dto.now_showing

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class NowShowingDto(
    @SerializedName("errorMessage")
    val errorMessage: String?,
    @SerializedName("items")
    val items: List<ItemNowShowing>?
)