package ro.vlad.core.data.remote.dto.details

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Company(
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String?
)