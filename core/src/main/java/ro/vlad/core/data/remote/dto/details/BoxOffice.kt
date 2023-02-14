package ro.vlad.core.data.remote.dto.details

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class BoxOffice(
    @SerializedName("budget")
    val budget: String?,
    @SerializedName("cumulativeWorldwideGross")
    val cumulativeWorldwideGross: String?,
    @SerializedName("grossUSA")
    val grossUSA: String?,
    @SerializedName("openingWeekendUSA")
    val openingWeekendUSA: String?
)