package com.example.android.gcit

import java.util.*

fun CovidDatum.prettyPrint(): String {

    if (this.country == null) {
        return "No data available right now, please try again later."
    }
    var confirmed: Int = 0
    var recovered: Int = 0
    var active: Int = 0

    provinces?.let {
         for (province in it) {
             confirmed += province.confirmed
             recovered += province.recovered
             active += province.active
         }
    }

    if (confirmed == 0 && recovered == 0 && active == 0) {
        return "No COVID data for $country on $date"
    }
    return "Country: $country\n\nFor Date: $date\n\nConfirmed: $confirmed\n\nRecovered: $recovered\n\nActive: $active"
}