package com.example.gcit_start

data class Country(val isoCode: String, val countryName: String, val shortCode: String) {
    fun getDisplayName(): String = "$countryName - $shortCode"
}