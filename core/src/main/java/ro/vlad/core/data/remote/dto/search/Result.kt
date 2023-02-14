package ro.vlad.core.data.remote.dto.search

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Result(
    @SerializedName("description")
    val description: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("resultType")
    val resultType: String?,
    @SerializedName("title")
    val title: String?
)