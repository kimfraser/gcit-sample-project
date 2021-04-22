package com.example.android.gcit

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast

class CountryRecyclerViewAdapter(private val countryList: List<Country>, private val countryDataInterface: CountryDataInterface) : RecyclerView.Adapter<CountryRecyclerViewAdapter.ViewHolder>() {


    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val countryNameTextView = itemView.findViewById<TextView>(R.id.country_name)
    }

    interface CountryDataInterface {
        fun getSelectedCountry(country: Country)
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val context: Context = p0.context
        val inflater = LayoutInflater.from(context)

        // Inflate the custom layout
        val contactView: View = inflater.inflate(R.layout.country_item, p0, false)

        // Return a new holder instance
        return ViewHolder(contactView)
    }

    override fun getItemCount(): Int = countryList.size

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val country: Country = countryList[p1]
        p0.countryNameTextView.text = country.getDisplayName()
        p0.countryNameTextView.setOnClickListener{
            countryDataInterface.getSelectedCountry(country)
        }
    }


}