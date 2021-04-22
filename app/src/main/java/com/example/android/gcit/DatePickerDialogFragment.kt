package com.example.android.gcit

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.ViewGroup
import android.widget.DatePicker
import java.util.*
import java.util.logging.Logger


class DatePickerDialogFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    var listener: DatePickerInterface? = null

    interface DatePickerInterface {
        fun getSelectedDate(year: Int, month: Int, day: Int)
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        listener = context as DatePickerInterface
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar: Calendar = Calendar.getInstance()
        val year: Int = calendar.get(Calendar.YEAR)
        val month: Int = calendar.get(Calendar.MONTH)
        val day: Int = calendar.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(activity,
                AlertDialog.THEME_HOLO_LIGHT, this, year, month, day)

         dpd.setTitle("Please select a date")

        dpd.datePicker.maxDate = System.currentTimeMillis();

        // Return the DatePickerDialog
        return dpd
    }
    override fun onDateSet(datePicker: DatePicker?, year: Int, month: Int, day: Int) {
        listener?.getSelectedDate(year, month, day)
    }
}