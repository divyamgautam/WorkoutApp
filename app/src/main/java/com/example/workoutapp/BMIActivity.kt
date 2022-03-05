package com.example.workoutapp

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.workoutapp.databinding.ActivityBmiBinding
import com.example.workoutapp.databinding.ActivityMainBinding

class BMIActivity: AppCompatActivity() {
    private var binding: ActivityBmiBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarBmiActivity)
        if (supportActionBar !== null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "CALCULATE BMI"
        }
        binding?.toolbarBmiActivity?.setNavigationOnClickListener {
            onBackPressed()
        }

        binding?.btnCalculate?.setOnClickListener{
            metricBMIFun()
        }

        binding?.rgUnits?.setOnCheckedChangeListener{_, checkedID: Int ->
            if(checkedID == R.id.rbUS){
                makeVisibleUSUnits()
                binding?.btnCalculate?.setOnClickListener{
                    USBMIFun()
                }
            }else{
                makeVisibleMetric()
                binding?.btnCalculate?.setOnClickListener{
                    metricBMIFun()
                }
            }
        }
    }


    private fun validateUnits(): Boolean{
        var isValid = true
        if(binding?.weight?.text.toString().isEmpty()){
            isValid = false
        }else if(binding?.height?.text.toString().isEmpty()){
            isValid = false
        }
        return isValid
    }

    private fun validateUSUnits(): Boolean{
        var isValid = true
        if(binding?.USweight?.text.toString().isEmpty()){
            isValid = false
        }else if(binding?.USHeightFeet?.text.toString().isEmpty()){
            isValid = false
        }else if(binding?.USHeightInches?.text.toString().isEmpty()){
            isValid = false
        }
        return isValid
    }

    private fun makeVisibleUSUnits(){
        binding?.tilHeight?.visibility = View.GONE
        binding?.tilWeight?.visibility = View.GONE
        binding?.tilUSHeightFeet?.visibility = View.VISIBLE
        binding?.tilUSHeightInches?.visibility = View.VISIBLE
        binding?.tilUSWeight?.visibility = View.VISIBLE
        binding?.llBMI?.visibility = View.INVISIBLE
    }

    private fun makeVisibleMetric(){
        binding?.tilHeight?.visibility = View.VISIBLE
        binding?.tilWeight?.visibility = View.VISIBLE
        binding?.tilUSHeightFeet?.visibility = View.GONE
        binding?.tilUSHeightInches?.visibility = View.GONE
        binding?.tilUSWeight?.visibility = View.GONE
        binding?.llBMI?.visibility = View.INVISIBLE
    }

    private fun USBMIFun(){
        if(validateUSUnits()) {
            val heightFeet: Float = binding?.USHeightFeet?.text.toString().toFloat()
            val heightInches: Float = binding?.USHeightInches?.text.toString().toFloat()
            val USweight: Float = binding?.USweight?.text.toString().toFloat()
            val heightTotal: Float = heightFeet*12 + heightInches
            val USbmi: Float = (USweight * 703)/(heightTotal * heightTotal)
            binding?.tvBMI?.text = USbmi.toString()
            binding?.btnCalculate?.text = "CONTINUE"
            if(USbmi < 18.5){
                binding?.category?.text = "UNDERWEIGHT"
                binding?.comment?.text = "Your weight is less than the healthy weight range. Increase calorie consumption and focus on resistance training  "
                binding?.category?.setTextColor(Color.parseColor("#FF018786"))
                binding?.tvBMI?.setTextColor(Color.parseColor("#FF018786"))
            }
            else if(USbmi > 18.5 && USbmi < 25.0){
                binding?.category?.text = "HEALTHY"
                binding?.comment?.text = "Your weight is in the healthy weight range. Good job, keep exercising!!  "
                binding?.category?.setTextColor(Color.parseColor("#00FF00"))
                binding?.tvBMI?.setTextColor(Color.parseColor("#00FF00"))

            }
            else if(USbmi > 25.0 && USbmi < 30){
                binding?.category?.text = "OVERWEIGHT"
                binding?.comment?.text = "Your weight is less more the healthy weight range. Decrease calorie consumption and focus on cardio training  "
                binding?.category?.setTextColor(Color.parseColor("#FF0000"))
                binding?.tvBMI?.setTextColor(Color.parseColor("#FF0000"))

            }
            else if(USbmi > 30){
                binding?.category?.text = "OBESE"
                binding?.comment?.text = "Your weight is more than the healthy weight range. Decrease calorie consumption and focus on cardio training  "
                binding?.category?.setTextColor(Color.parseColor("#FF0000"))
                binding?.tvBMI?.setTextColor(Color.parseColor("#FF0000"))

            }
            binding?.llBMI?.visibility = View.VISIBLE
        }else{
            Toast.makeText(this, "Please enter height and weight", Toast.LENGTH_SHORT).show()
        }
    }

    private fun metricBMIFun(){
        if(validateUnits()) {
            val height: Float = binding?.height?.text.toString().toFloat()
            val weight: Float = binding?.weight?.text.toString().toFloat()
            val bmi: Float = weight / ((height / 100) * (height / 100))
            binding?.tvBMI?.text = bmi.toString()
            binding?.btnCalculate?.text = "CONTINUE"
            if(bmi < 18.5){
                binding?.category?.text = "UNDERWEIGHT"
                binding?.comment?.text = "Your weight is less than the healthy weight range. Increase calorie consumption and focus on resistance training  "
                binding?.category?.setTextColor(Color.parseColor("#FF018786"))
                binding?.tvBMI?.setTextColor(Color.parseColor("#FF018786"))
            }
            else if(bmi > 18.5 && bmi < 25.0){
                binding?.category?.text = "HEALTHY"
                binding?.comment?.text = "Your weight is in the healthy weight range. Good job, keep exercising!!  "
                binding?.category?.setTextColor(Color.parseColor("#00FF00"))
                binding?.tvBMI?.setTextColor(Color.parseColor("#00FF00"))

            }
            else if(bmi > 25.0 && bmi < 30){
                binding?.category?.text = "OVERWEIGHT"
                binding?.comment?.text = "Your weight is less more the healthy weight range. Decrease calorie consumption and focus on cardio training  "
                binding?.category?.setTextColor(Color.parseColor("#FF0000"))
                binding?.tvBMI?.setTextColor(Color.parseColor("#FF0000"))

            }
            else if(bmi > 30){
                binding?.category?.text = "OBESE"
                binding?.comment?.text = "Your weight is more than the healthy weight range. Decrease calorie consumption and focus on cardio training  "
                binding?.category?.setTextColor(Color.parseColor("#FF0000"))
                binding?.tvBMI?.setTextColor(Color.parseColor("#FF0000"))

            }
            binding?.llBMI?.visibility = View.VISIBLE
        }else{
            Toast.makeText(this, "Please enter height and weight", Toast.LENGTH_SHORT).show()
        }
    }
}