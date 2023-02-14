package ro.vlad.core.data.remote.dto.details

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Writer(
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String?
)