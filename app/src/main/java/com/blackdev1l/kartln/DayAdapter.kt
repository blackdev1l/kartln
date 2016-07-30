package com.blackdev1l.kartln

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by cristian on 6/21/16.
 *
 */

class DayAdapter(val myDataSet: Vector<Day>) : RecyclerView.Adapter<DayAdapter.ViewHolder>() {
    private val  mDataset: Vector<Day> = myDataSet

    open class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        var cv = itemView!!.findViewById(R.id.card_view) as CardView
        var dateTextView = itemView!!.findViewById(R.id.date) as TextView
        var firstMarkView = itemView!!.findViewById(R.id.firstMark) as TextView
        var secondMarkView = itemView!!.findViewById(R.id.secondMark) as TextView
        var thirdMarkView = itemView!!.findViewById(R.id.thirdMark) as TextView
        var fourthMarkView = itemView!!.findViewById(R.id.fourthMark) as TextView

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val day = mDataset.get(position)

        val dateFormat = "EEE, d MMM yyyy"
        val markFormat = "h:mm a"
        val dateFormttaer = SimpleDateFormat(dateFormat,Locale.ITALY)
        val markFormatter = SimpleDateFormat(markFormat,Locale.ITALY)

        val date = dateFormttaer.format(day.date)


        var firstMark = String()
        var secondMark = String()
        var thirdMark = String()
        var fourthMark = String()

        day.marks.forEachIndexed { i, date ->
            when (i) {
                0 -> firstMark = markFormatter.format(date)
                1 -> secondMark = markFormatter.format(date)
                2 -> thirdMark = markFormatter.format(date)
                3 -> fourthMark = markFormatter.format(date)
            }
        }


        holder.dateTextView.text = date.toString()
        holder.firstMarkView.text = firstMark.toString()
        holder.secondMarkView.text = secondMark.toString()
        holder.thirdMarkView.text = thirdMark.toString()
        holder.fourthMarkView.text = fourthMark.toString()


    }

    override fun getItemCount(): Int { return mDataset.count() }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder? {
        val v = LayoutInflater.from(parent!!.context).inflate(R.layout.day_card,parent,false)
        return DayAdapter.ViewHolder(v)
    }
}