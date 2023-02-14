package ro.vlad.core.data.remote.dto.now_showing

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Director(
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String?
)