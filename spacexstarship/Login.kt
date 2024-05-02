package com.flplanet.spacexstarship

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.flplanet.spacexstarship.databinding.LoginBinding

class Login: AppCompatActivity() {

    private lateinit var binding: LoginBinding
    private val ticket by lazy { getSharedPreferences("tickets", Context.MODE_PRIVATE) }
    var userName = "admin"
    var password = "1234"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        created()
    }

    private fun created() {

        binding.forgotPassword.setOnClickListener { forgotButton() }

        binding.buttonLogin.setOnClickListener {
            if(binding.inputUsername.text.toString() == userName){
                if(binding.inputPassword.text.toString() == password) {

                    if(ticket.getInt("prev",1)==2) {
                        val intent = Intent(this, Profile::class.java)
                        startActivity(intent)
                    }
                    else{
                        val intent = Intent(this, Booking::class.java)
                        startActivity(intent)
                    }

                    ticket.edit().putBoolean("loginStatus", true).apply()
                }
            }
            else
                Toast.makeText(this, "wrong email/password, try again!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun forgotButton() {
        if(binding.inputUsername.text.toString() == "craig")
            Toast.makeText(this, "You're Gae", Toast.LENGTH_SHORT).show()
        else if (binding.inputUsername.text.toString() == "nathan")
            Toast.makeText(this, "arre pozdya", Toast.LENGTH_SHORT).show()
        else if (binding.inputUsername.text.toString() == "krishna")
            Toast.makeText(this, "the sun will shine on us again.", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(this, "Skill issues.", Toast.LENGTH_SHORT).show()

    }
}