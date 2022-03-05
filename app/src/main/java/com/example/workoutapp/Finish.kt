package com.example.workoutapp

import java.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope

import com.example.workoutapp.databinding.ActivityFinishedBinding
import kotlinx.coroutines.launch
import java.util.*

class Finish : AppCompatActivity() {
    private var binding: ActivityFinishedBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishedBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbarFinishActivity)
        if (supportActionBar !== null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbarFinishActivity?.setNavigationOnClickListener {
            onBackPressed()
        }
        binding?.btnFinish?.setOnClickListener{
            finish()
        }
        val dao = (application as WorkoutApp).db.historyDao()
        addDateToDatabase(dao)
    }
    private fun addDateToDatabase(historyDao: HistoryDao){
        val cal = Calendar.getInstance()
        val dateTime = cal.time
        Log.e("Date: ",""+dateTime)
        val sdf = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault())
        val date = sdf.format(dateTime)
        Log.e("Formatted Date: ",""+date)
        lifecycleScope.launch{
            historyDao.insert(HistoryEntity(date))
        }

    }
}

