package com.flplanet.spacexstarship

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.flplanet.spacexstarship.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val ticket by lazy { getSharedPreferences("tickets", Context.MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        created()
    }

    override fun onResume() {
        created()
        super.onResume()
    }
    private fun created () {
        binding.logo.setOnClickListener {
            Toast.makeText(this, "Flavian says hi !", Toast.LENGTH_SHORT).show()
        }
        val loginStatus = ticket.getBoolean("loginStatus", false)

        binding.fab.setOnClickListener {
            if(loginStatus){
                val intent = Intent(this, Profile::class.java)
                startActivity(intent)
            }
            else {
                ticket.edit().putInt("prev",2).apply()
                loginOff()
            }

        }

        binding.buttonBook.setOnClickListener {
            if(!loginStatus) {
                ticket.edit().putInt("prev",1).apply()
                loginOff()
            }

            else{
                val intent = Intent(this, Booking::class.java)
                startActivity(intent)
            }
        }
    }
    private fun loginOff(){
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
    }

}