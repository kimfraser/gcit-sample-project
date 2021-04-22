package com.example.android.gcit

import android.app.AlertDialog
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.format.DateFormat
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.android.gcit.Constants.Companion.RAPID_API_HOST_NAME
import com.example.android.gcit.Constants.Companion.RAPID_API_HOST_VALUE
import com.example.android.gcit.Constants.Companion.RAPID_API_KEY
import com.example.android.gcit.Constants.Companion.RAPID_API_KEY_NAME
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.util.*


class MainActivity : AppCompatActivity(), CountryRecyclerViewAdapter.CountryDataInterface, DatePickerDialogFragment.DatePickerInterface {

    var countrySelected: Country? = null
    var dateSelected: GregorianCalendar? = null
    lateinit var backgroundTask: AsyncTask<String?, Void?, Response?>

    lateinit var countrySelectedTextView: TextView
    lateinit var dateSelectedTextView: TextView
    lateinit var countryListView: RecyclerView
    lateinit var dateButton: Button
    lateinit var submit: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViews()



        // Create country list
        val countries: MutableList<Country> = mutableListOf()
        val isoCountries: Array<String> = Locale.getISOCountries()
        for (country in isoCountries) {
            val locale = Locale("en", country)
            val iso: String = locale.isO3Country
            val code: String = locale.country
            val name: String = locale.displayCountry
            if (iso.isNotEmpty() && code.isNotEmpty() && name.isNotEmpty()) {
                countries.add(Country(iso, name, code))
            }
        }

        // Create adapter for recycler view
        val countryRecyclerViewAdapter = CountryRecyclerViewAdapter(countries, this)
        countryListView.adapter = countryRecyclerViewAdapter
        countryListView.layoutManager = LinearLayoutManager(this)


        // Set click listener for enter button
        submit.setOnClickListener {
            if (dataIsValid()) {
                // Make API Call
                val params = arrayOf(DateFormat.format("yyyy-MM-dd", dateSelected).toString(), countrySelected!!.countryName)
                backgroundTask = Task(object : Task.AsyncResponse {
                    override fun processFinish(output: CovidDatum) {
                        AlertDialog.Builder(this@MainActivity)
                                .setMessage(output.prettyPrint())
                                .setNeutralButton("OK", null)
                                .create()
                                .show()
                    }
                })
                backgroundTask.execute(*params)
            }
        }
    }

    private fun setupViews() {
        countryListView = findViewById(R.id.countryListView)
        countrySelectedTextView = findViewById(R.id.countrySelected)
        dateSelectedTextView = findViewById(R.id.dateSelected)
        dateButton = findViewById(R.id.dateButton)
        submit = findViewById(R.id.submit)

        // Set click listener for date picker button
        dateButton.setOnClickListener {
            val datePickerDialogFragment = DatePickerDialogFragment()
            datePickerDialogFragment.show(supportFragmentManager, "Date Picker")
        }
    }

    private fun dataIsValid(): Boolean {
        if (countrySelected == null && dateSelected == null) {
            AlertDialog.Builder(this)
                    .setTitle("ERROR: Missing Country and Date")
                    .setCancelable(false)
                    .setMessage("Please select a country and date")
                    .setNeutralButton("OK", null)
                    .show()
            return false
        }

        if (countrySelected == null) {
            AlertDialog.Builder(this)
                    .setTitle("ERROR: Missing Country")
                    .setCancelable(false)
                    .setMessage("Please select a country")
                    .setNeutralButton("OK", null)
                    .show()
            return false
        }

        if (dateSelected == null) {
            AlertDialog.Builder(this)
                    .setTitle("ERROR: Missing Date")
                    .setCancelable(false)
                    .setMessage("Please select a date")
                    .setNeutralButton("OK", null)
                    .show()
            return false
        }

        return true
    }

    override fun getSelectedCountry(country: Country) {
        countrySelected = country
        countrySelectedTextView.text = "Country Selected: " + country.countryName
    }

    override fun getSelectedDate(year: Int, month: Int, day: Int) {
        dateSelected = GregorianCalendar(year, month, day)
        dateSelectedTextView.visibility = View.VISIBLE
        dateSelectedTextView.text = DateFormat.format("yyyy-MM-dd", dateSelected)
    }
}

private class Task(val delegate: AsyncResponse) : AsyncTask<String?, Void?, Response?>() {

    interface AsyncResponse {
        fun processFinish(output: CovidDatum)
    }

    override fun onPostExecute(result: Response?) {
        // error handling
        val stringBody = result?.body()?.string()
        if (stringBody?.contains("Country not found") == true || stringBody?.contains("not subscribed") == true) {
            val data = CovidDatum()
            delegate.processFinish(data)
        } else if (stringBody != null) {
            val gson = GsonBuilder().create()
            val data: CovidDatum = gson.fromJson(stringBody.drop(1).dropLast(1), CovidDatum::class.java)
            delegate.processFinish(data)
        }
    }

    override fun doInBackground(vararg params: String?): Response? {
        val date = params[0]
        val country = params[1]
        val client = OkHttpClient()
        val request = Request.Builder()
                .url("https://covid-19-data.p.rapidapi.com/report/country/name?date=$date&name=$country")
                .get()
                .addHeader(RAPID_API_KEY_NAME, RAPID_API_KEY)
                .addHeader(RAPID_API_HOST_NAME, RAPID_API_HOST_VALUE)
                .build()

        return client.newCall(request).execute()
    }
}