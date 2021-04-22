package com.example.gcit_start

fun CovidDatum.prettyPrint(): String {

    if (this.country == null) {
        return "Could not retrieve data, please try again later."
    }
    var confirmed = 0
    var recovered = 0
    var active = 0

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