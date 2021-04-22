package com.example.android.gcit

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class CountryRecyclerViewAdapter(private val countryList: List<Country>, private val countryDataInterface: CountryDataInterface) : RecyclerView.Adapter<CountryRecyclerViewAdapter.ViewHolder>() {


    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val countryNameTextView: TextView = itemView.findViewById(R.id.country_name)
    }

    interface CountryDataInterface {
        fun getSelectedCountry(country: Country)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context: Context = parent.context
        val inflater = LayoutInflater.from(context)

        // Inflate the custom layout
        val contactView: View = inflater.inflate(R.layout.country_item, parent, false)

        // Return a new holder instance
        return ViewHolder(contactView)
    }

    override fun getItemCount(): Int = countryList.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val country: Country = countryList[position]
        viewHolder.countryNameTextView.text = country.getDisplayName()
        viewHolder.countryNameTextView.setOnClickListener{
            countryDataInterface.getSelectedCountry(country)
        }
    }


}