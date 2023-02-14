package ro.vlad.core.data.remote.dto.details

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Actor(
    @SerializedName("asCharacter")
    val asCharacter: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("name")
    val name: String?
)