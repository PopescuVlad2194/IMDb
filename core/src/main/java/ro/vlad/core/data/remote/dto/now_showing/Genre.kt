package ro.vlad.core.data.remote.dto.now_showing

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Genre(
    @SerializedName("key")
    val key: String?,
    @SerializedName("value")
    val value: String?
)