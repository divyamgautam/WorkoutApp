package com.example.workoutapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.workoutapp.databinding.ActivityBmiBinding
import com.example.workoutapp.databinding.HistoryDateLayoutBinding

class HistoryAdapter(private val items: ArrayList<String>): RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {
    class ViewHolder(binding: HistoryDateLayoutBinding):RecyclerView.ViewHolder(binding.root){
        val llHistoryItem = binding.llHistoryItem
        val tvItem = binding.tvItem
        val tvPosition = binding.tvPosition
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return  ViewHolder(HistoryDateLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val date: String = items.get(position)
        holder.tvPosition.text = (position + 1).toString()
        holder.tvItem.text = date

        if(position % 2 == 0){
            holder.llHistoryItem.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.light_grey))
        }
        else{
            holder.llHistoryItem.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.white))
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }

}