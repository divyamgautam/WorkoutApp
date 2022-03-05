package com.example.workoutapp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.workoutapp.databinding.ItemExerciseStatusBinding

class ExerciseStatusAdapter(val items: ArrayList<ExerciseModel>):
    RecyclerView.Adapter<ExerciseStatusAdapter.ViewHolder>() {
        class ViewHolder(binding: ItemExerciseStatusBinding):
            RecyclerView.ViewHolder(binding.root){
                val tvItem = binding.tvItem
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemExerciseStatusBinding.inflate(
            LayoutInflater.from(parent.context),parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: ExerciseModel = items[position]
        holder.tvItem.text = model.getId().toString()
        when{
            model.getIsCompleted()->{
                holder.tvItem.background =
                    ContextCompat.getDrawable(holder.itemView.context, R.drawable.circular_color_green_background)
                holder.tvItem.setTextColor(Color.parseColor("#FFFFFF"))
            }
            model.getIsSelected()->{
                holder.tvItem.background =
                    ContextCompat.getDrawable(holder.itemView.context, R.drawable.circular_color_darkgreen_background)
            }

        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}