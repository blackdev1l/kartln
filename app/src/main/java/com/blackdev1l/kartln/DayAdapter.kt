package com.blackdev1l.kartln

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

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
        var firstHalfView = itemView!!.findViewById(R.id.firstHalf) as TextView
        var secondHalfView = itemView!!.findViewById(R.id.secondHalf) as TextView

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val day = mDataset.get(position)

        val dateFormat = "EEE, d MMM yyyy"
        val markFormat = "HH:mm"
        val dateFormttaer = SimpleDateFormat(dateFormat,Locale.ITALY)
        val markFormatter = SimpleDateFormat(markFormat,Locale.ITALY)

        val date = dateFormttaer.format(day.date)


        var firstMark = String()
        var secondMark = String()
        var thirdMark = String()
        var fourthMark = String()
        var firstHalf = String()
        var secondHalf = String()

        day.marks.forEachIndexed { i, date ->
            when (i) {
                0 -> firstMark = markFormatter.format(date)
                1 ->  {
                    secondMark = markFormatter.format(date)
                    val hours = getDateDiff(day.marks.firstElement(),date,TimeUnit.HOURS)
                    val minutes = getDateDiff(day.marks.firstElement(),date,TimeUnit.MINUTES)
                    firstHalf = String.format("%s:%s",hours.toString(),minutes.toString())
                }
                2 -> thirdMark = markFormatter.format(date)
                3 -> {
                    fourthMark = markFormatter.format(date)
                    val hours = getDateDiff(day.marks.get(2),date,TimeUnit.HOURS)
                    val minutes = getDateDiff(day.marks.get(2),date,TimeUnit.MINUTES)
                    secondHalf = String.format("%s:%s",hours.toString(),minutes.toString())
                }
            }
        }


        holder.dateTextView.text = date.toString()
        holder.firstMarkView.text = firstMark
        holder.secondMarkView.text = secondMark
        holder.thirdMarkView.text = thirdMark
        holder.fourthMarkView.text = fourthMark
        holder.firstHalfView.text = firstHalf
        holder.secondHalfView.text = secondHalf


    }

    override fun getItemCount(): Int { return mDataset.count() }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder? {
        val v = LayoutInflater.from(parent!!.context).inflate(R.layout.day_card,parent,false)
        return DayAdapter.ViewHolder(v)
    }

    /**
     * Get a diff between two dates
     * @param date1 the oldest date
     * *
     * @param date2 the newest date
     * *
     * @param timeUnit the unit in which you want the diff
     * *
     * @return the diff value, in the provided unit
     */
    fun getDateDiff(date1: Date, date2: Date, timeUnit: TimeUnit): Long {
        val diffInMillies = date2.time - date1.time
        return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS)
    }
}