package ro.vlad.core.data.remote.dto.box_office

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class BoxOfficeDto(
    @SerializedName("errorMessage")
    val errorMessage: String?,
    @SerializedName("items")
    val items: List<ItemBoxOffice>?
)