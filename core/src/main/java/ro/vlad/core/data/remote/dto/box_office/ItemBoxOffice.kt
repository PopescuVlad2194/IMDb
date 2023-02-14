package ro.vlad.core.data.remote.dto.box_office

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ItemBoxOffice(
    @SerializedName("gross")
    val gross: String?,
    @SerializedName("id")
    val id: String,
    @SerializedName("image")
    val image: String?,
    @SerializedName("rank")
    val rank: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("weekend")
    val weekend: String?,
    @SerializedName("weeks")
    val weeks: String?
)