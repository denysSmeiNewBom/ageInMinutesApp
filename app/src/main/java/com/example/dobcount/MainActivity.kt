package com.example.dobcount

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import android.app.DatePickerDialog as DatePickerDialog1

class MainActivity : AppCompatActivity() {
    private var vtSelectedDate: TextView? = null
    private var vtMinutes: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnSelectDate: Button = findViewById(R.id.button)

        vtSelectedDate = findViewById(R.id.dateFormatted)
        vtMinutes = findViewById(R.id.inMinutes)
        val myCalendar = Calendar.getInstance()
        val day = myCalendar.get(Calendar.DATE)
        val month = myCalendar.get(Calendar.MONTH)
        val year = myCalendar.get(Calendar.YEAR)
        val selectedDateFormatted = "$day.${month + 1}.$year"
        vtSelectedDate?.text = selectedDateFormatted

        btnSelectDate.setOnClickListener {
            clickDatePicker()
        }
    }

    fun clickDatePicker() {
        val myCalendar = Calendar.getInstance()
        val day = myCalendar.get(Calendar.DATE)
        val month = myCalendar.get(Calendar.MONTH)
        val year = myCalendar.get(Calendar.YEAR)
        val dpd = DatePickerDialog1(
            this, { view, year, month, day ->
                val selectedDateFormatted = "$day.${month + 1}.$year"
                vtSelectedDate?.text = selectedDateFormatted

                Toast.makeText(
                    this,
                    "Calculating minutes till $selectedDateFormatted",
                    Toast.LENGTH_LONG
                ).show()

                val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH)
                val date = sdf.parse(selectedDateFormatted)
                date?.let {
                    val selectedDateInMinutes = date.time / 60_000

                    sdf.parse(sdf.format(System.currentTimeMillis()))?.let { currentDate ->
                        val currentDateInMinutes = currentDate.time / 60_000

                        vtMinutes?.text = "${currentDateInMinutes - selectedDateInMinutes}"
                    }
                }
            }, year, month, day
        )
        dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000
        dpd.show()
    }
}