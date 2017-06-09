package com.jakubwyc.mysecretvault.treasure

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.jakubwyc.mysecretvault.R
import com.jakubwyc.mysecretvault.model.Treasure
import java.text.SimpleDateFormat
import java.util.*

class TreasureAdapter(private val treasures: MutableList<Treasure>, private val listener: TreasureOnClickListener) : RecyclerView.Adapter<TreasureAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.treasure_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.text.text = treasures[position].text
        val dateString = SimpleDateFormat("dd.MM.yyyy HH:mm").format(Date(treasures[position].date))
        holder.date.text = dateString
    }

    override fun getItemCount(): Int {
        return treasures.size
    }

    fun addTreasure(treasureSwiped: Treasure, adapterPosition: Int) {
        treasures.add(adapterPosition, treasureSwiped)
        notifyItemInserted(adapterPosition)
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        val text: TextView = v.findViewById(R.id.text) as TextView
        val date: TextView = v.findViewById(R.id.date) as TextView

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            listener.onTreasureClick(treasures[adapterPosition])
        }
    }

    fun getTreasureAt(i: Int): Treasure {
        return treasures[i]
    }

    fun removeTreasure(treasure: Treasure, adapterPosition: Int) {
        treasures.remove(treasure)
        notifyItemRemoved(adapterPosition)
    }

}
