package com.example.android.gcit

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.gcit_start.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO: 1 - Create layout
        setContentView(R.layout.activity_main)

        // TODO: 2 - setup views
        setupViews()

        // TODO: 3 - Create country list
        val countries = createCountryList()

        // TODO: 5 - Create adapter for recycler view

        // TODO: 8 - Set click listener and make API call
    }

    private fun setupViews() {


        // TODO: 4 - Create Date picker dialog fragment
    }

    private fun dataIsValid(): Boolean {
        // TODO: 6 - Check if data is valid
        return false
    }

    private fun createCountryList(): List<Country> {
        return emptyList()
    }
}

// TODO: 7 - Create Async Task