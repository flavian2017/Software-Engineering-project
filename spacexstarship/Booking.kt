package com.flplanet.spacexstarship

import android.R
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.flplanet.spacexstarship.databinding.ActivityMainBinding
import com.flplanet.spacexstarship.databinding.BookingBinding
import java.util.*

class Booking: AppCompatActivity() {

    private lateinit var binding: BookingBinding
    private val ticket by lazy { getSharedPreferences("tickets", Context.MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = BookingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupSpinner()
        binding.buttonConfirmBooking.setOnClickListener { saveInfo() }
    }

    private fun setupSpinner() {
        val spinnerValues = arrayOf("GOA","MUMBAI","BANGALORE","CHENNAI","DELHI", "LONDON", "NORWAY", "USA", "JAPAN")
        val spinnerAdapter = ArrayAdapter(this, R.layout.simple_spinner_dropdown_item,spinnerValues)
        binding.SourceSpinner.adapter = spinnerAdapter
        binding.DestnationSpinner.adapter = spinnerAdapter

    }

    private fun saveInfo(){
        val name = binding.inputName.text.toString()
        val number = binding.inputPhoneNumber.text.toString()
        val from = binding.SourceSpinner.selectedItem.toString()
        val to = binding.DestnationSpinner.selectedItem.toString()
        val date = binding.date.text.toString()
        if(name.isNotEmpty() && number.isNotEmpty() && date.isNotEmpty()){
            var size = ticket.getInt("size", 0)
            size += 1
            ticket.edit()
                .putString("$size - 1", name)
                .putString("$size - 2", number)
                .putString("$size - 3", from)
                .putString("$size - 4", to)
                .putString("$size - 5", date)
                .putInt("size",size)
                .apply()


            Toast.makeText(this, "Your ticket has been booked", Toast.LENGTH_SHORT).show()

            Handler().postDelayed({
                val intent = Intent(this, Profile::class.java)
                startActivity(intent)             }, 1000)
        }
        else
            Toast.makeText(this, "please don't leave any blanks", Toast.LENGTH_SHORT).show()
    }
}