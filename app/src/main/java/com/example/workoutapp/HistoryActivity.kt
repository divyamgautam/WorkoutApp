package com.example.workoutapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workoutapp.databinding.ActivityBmiBinding
import com.example.workoutapp.databinding.ActivityHistoryBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HistoryActivity: AppCompatActivity() {
    private var binding: ActivityHistoryBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarHistoryActivity)
        if (supportActionBar !== null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "WORKOUT HISTORY"
        }
        binding?.toolbarHistoryActivity?.setNavigationOnClickListener{
            onBackPressed()
        }
        val dao = (application as WorkoutApp).db.historyDao()
        getAllCompletedDates(dao)
    }

    private fun getAllCompletedDates(historyDao: HistoryDao){
        lifecycleScope.launch{
            historyDao.fetchAllDates().collect {allCompletedDatesList ->
               if(allCompletedDatesList.isNotEmpty()){
                   binding?.rvDatabase?.visibility = View.VISIBLE
                   binding?.tvNoRecords?.visibility = View.INVISIBLE

                   binding?.rvDatabase?.layoutManager = LinearLayoutManager(this@HistoryActivity)

                   val dates = ArrayList<String>()
                   for(date in allCompletedDatesList){
                       dates.add(date.date)
                   }

                   val historyAdapter = HistoryAdapter(dates)

                   binding?.rvDatabase?.adapter = historyAdapter

               }
                else{
                   binding?.rvDatabase?.visibility = View.GONE
                   binding?.tvNoRecords?.visibility = View.VISIBLE
               }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
