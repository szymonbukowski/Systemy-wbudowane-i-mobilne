package com.example.bmi

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bmi.MainActivity.Companion.imperial
import com.example.bmi.MainActivity.Companion.metric


class HistoryAdapter(private val history: ArrayList<BmiDataPack>) : RecyclerView.Adapter<HistoryAdapter.BmiViewHolder>() {


    class BmiViewHolder(val view: View, val context:Context) : RecyclerView.ViewHolder(view){
        val result :TextView = view.findViewById(R.id.rcv_bmiResult)
        val mass :TextView = view.findViewById(R.id.rcv_mass_value)
        val height :TextView = view.findViewById(R.id.rcv_height_value)
        val category :TextView = view.findViewById(R.id.rcv_category)
    }



    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): HistoryAdapter.BmiViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.history_recycler_layout,parent,false)
        return BmiViewHolder(view,parent.context)
    }

    override fun onBindViewHolder(holder: BmiViewHolder, position: Int) {

        val historyRecord = history[position]
        holder.result.text = historyRecord.result
        holder.category.text = historyRecord.category
        holder.result.setTextColor(historyRecord.color)
        holder.category.setTextColor(historyRecord.color)

        when(historyRecord.unit){
            metric   ->{
                holder.mass.text = historyRecord.mass + " kg"
                holder.height.text = historyRecord.height + " cm"
            }
            imperial ->{
                holder.mass.text = historyRecord.mass + " lb"
                holder.height.text = historyRecord.height + " in"
                }
        }
    }

    override fun getItemCount() = history.size
}