package ro.vlad.core.data.remote.dto.coming_soon

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Star(
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String?
)