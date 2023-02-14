package ro.vlad.core.data.remote.dto.details

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Language(
    @SerializedName("key")
    val key: String?,
    @SerializedName("value")
    val value: String?
)