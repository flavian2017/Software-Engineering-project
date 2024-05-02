package com.flplanet.spacexstarship

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.recyclerview.widget.RecyclerView
import com.flavian.SpaceXStarship.Tickets
import com.flplanet.spacexstarship.databinding.RecyclerViewBinding
import com.google.android.material.snackbar.Snackbar

class Profile : AppCompatActivity() {

    private lateinit var binding: RecyclerViewBinding
    private val ticket by lazy { getSharedPreferences("tickets", Context.MODE_PRIVATE) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RecyclerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val arraySize = ticket.getInt("size", 2)
        if(arraySize!=0){
            setUpList()
            }
        else
            binding.heading.text = "no bookings yet!"
        binding.buttonLogOut.setOnClickListener { alertDialog() }
        binding.swipeRefresh.setOnRefreshListener { refreshPage() }
        binding.buttonBook.setOnClickListener {
            val intent = Intent(this, Booking::class.java)
            startActivity(intent)
        }

    }

    private fun refreshPage() {
        binding.swipeRefresh.isRefreshing = false
        setUpList()
    }

    private fun setUpList() {


        val size = ticket.getInt("size", 2)

        val ticketList = arrayListOf<Tickets>()
        for (i in 1..size) {
            val name = ticket?.getString("$i - 1","").toString()
            val number = ticket?.getString("$i - 2","").toString()
            val from = ticket?.getString("$i - 3","").toString()
            val to = ticket?.getString("$i - 4","").toString()
            val date = ticket?.getString("$i - 5","").toString()
            ticketList.add(Tickets(name, number, to, from, date))
        }

        val list: RecyclerView = binding.recyclerView
        val adapter = RecyclerViewActivity(ticketList)
        list.adapter = adapter
    }

    private fun alertDialog() {
        AlertDialog.Builder(this)
            .setTitle("Logging Out")
            .setMessage("are you sure you want to Logout?")
            .setPositiveButton("yes") { _, _ ->

                ticket.edit().putBoolean("loginStatus", false).apply()

                val snackbar = Snackbar.make(binding.recyclerView,"Logged Out successfully!",
                    Snackbar.LENGTH_LONG)
                snackbar.anchorView = binding.buttonLogOut
                snackbar.setAction("undo"){
                    //some code to undo
                }
                snackbar.show()

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

            }
            .setNegativeButton("no", null)
            .show()
    }

}