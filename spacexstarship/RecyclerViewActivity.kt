package com.flplanet.spacexstarship

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.flavian.SpaceXStarship.Tickets
import com.google.android.material.snackbar.Snackbar


class RecyclerViewActivity(entries: List<Tickets>) :
    RecyclerView.Adapter<RecyclerViewActivity.RecyclerViewHolder>() {
    private val entries: List<Tickets>

    init {
        this.entries = entries
    }

    override fun getItemCount(): Int {
        return entries.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return RecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.bind(entries[position], position)
    }

    class RecyclerViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val name: TextView
        private val to: TextView
        private val from: TextView
        private val number: TextView
        private val date: TextView
        private val remove: TextView
        private val activity =  itemView.context
        private val a = activity.getSharedPreferences("tickets", Context.MODE_PRIVATE)

        init {
            name = itemView.findViewById(R.id.name)
            number = itemView.findViewById(R.id.number)
            to = itemView.findViewById(R.id.dest)
            from = itemView.findViewById(R.id.from)
            date = itemView.findViewById(R.id.date)
            remove = itemView.findViewById(R.id.remove)
            remove.setOnClickListener { alertDialog() }
        }

            private fun remove() {
                val position = adapterPosition+1
                val size = a.getInt("size",0)
                a.edit()
                    .remove("$position - 1")
                    .remove("$position - 2")
                    .remove("$position - 3")
                    .remove("$position - 4")
                    .remove("$position - 5")
                    .apply()

                var j = position + 1
                for (i in position..size) {
                    val name = a.getString("$j - 1", "")
                    val number = a.getString("$j - 2", "")
                    val from = a.getString("$j - 3", "")
                    val to = a.getString("$j - 4", "")
                    val date = a.getString("$j - 5", "")

                    a.edit()
                        .putString("$i - 1", name)
                        .putString("$i - 2", number)
                        .putString("$i - 3", from)
                        .putString("$i - 4", to)
                        .putString("$i - 5", date)
                        .apply()
                    j++
                }
                a.edit()
                    .putInt("size", size - 1)
                    .apply()

                Toast.makeText(this.activity, "booking cancelled. PLease refresh", Toast.LENGTH_SHORT).show()

                }
        private fun alertDialog() {
            AlertDialog.Builder(this.activity)
                .setTitle("Cancel Booking ?")
                .setMessage("are you sure you want to cancel?")
                .setPositiveButton("yes") { _, _ ->
                    remove()
                }
                .setNegativeButton("no", null)
                .show()
        }

        fun bind(entry: Tickets, position: Int) {
            name.text = entry.name
            number.text = entry.number
            to.text = entry.to
            from.text = entry.from
            date.text = entry.date
            remove.text = "cancel"
        }
    }
}
