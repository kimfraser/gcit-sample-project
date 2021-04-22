package com.example.android.gcit

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class CovidDatum: Serializable {
    @SerializedName("country")
    @Expose
    var country: String? = null

    @SerializedName("provinces")
    @Expose
    var provinces: kotlin.collections.List<Province>? = null

    @SerializedName("latitude")
    @Expose
    var latitude: Double? = null

    @SerializedName("longitude")
    @Expose
    var longitude: Double? = null

    @SerializedName("date")
    @Expose
    var date: String? = null
}

class Province: Serializable {
    @SerializedName("province")
    @Expose
    var province: String? = null

    @SerializedName("confirmed")
    @Expose
    var confirmed: Int = 0

    @SerializedName("recovered")
    @Expose
    var recovered: Int = 0

    @SerializedName("deaths")
    @Expose
    var deaths: Int = 0

    @SerializedName("active")
    @Expose
    var active: Int = 0
}
