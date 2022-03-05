package com.example.workoutapp

import android.app.Dialog
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workoutapp.databinding.ActivityExerciseBinding
import com.example.workoutapp.databinding.DialogCustomBackConformationBinding
import org.w3c.dom.Text
import java.util.*
import kotlin.collections.ArrayList
import android.content.Intent as Intent

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
        private var binding: ActivityExerciseBinding? = null
        private var restTimer: CountDownTimer? = null
        private var restProgress = 0
        private var exerciseTimer: CountDownTimer? = null
        private var exerciseProgress = 0
        private var exerciseList: ArrayList<ExerciseModel>? = null
        private var currentExercisePosition = -1
        private var tts: TextToSpeech? = null
        private var player: MediaPlayer? = null
        private var exerciseAdapter: ExerciseStatusAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        if (supportActionBar !== null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        setSupportActionBar(binding?.tb)
        binding?.tb?.setNavigationOnClickListener {
            backButton()
        }
        binding?.flRestView?.visibility = View.VISIBLE


        tts = TextToSpeech(this,this)
        exerciseList = Constants.defaultExerciseList()
        setUpRestView()
        setupExerciseStatusRecyclerView()

    }

        private fun setExerciseProgressBar(){
            binding?.progressBarExercise?.progress = exerciseProgress
            exerciseTimer = object: CountDownTimer(30000, 1000){
                override fun onTick(millisUntilFinished: Long) {
                    exerciseProgress++
                    binding?.progressBarExercise?.progress = 30 - exerciseProgress
                    binding?.tvTimerExercise?.text = (30 - exerciseProgress).toString()
                }

                override fun onFinish() {
                    binding?.ivImage?.visibility = View.GONE
                    if(currentExercisePosition < exerciseList?.size!! - 1){
                        exerciseList!![currentExercisePosition].setIsSelected(false)
                        exerciseList!![currentExercisePosition].setIsCompleted(true)
                        exerciseAdapter!!.notifyDataSetChanged()
                        setUpRestView()
                    }else{
                        finish()
                        val intent = Intent(this@ExerciseActivity, Finish::class.java)
                        startActivity(intent)
                    }
                }

            }.start()
        }

        private fun setupExerciseStatusRecyclerView(){
            binding?.rvExerciseStatus?.layoutManager =
                LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false)
            exerciseAdapter = ExerciseStatusAdapter(exerciseList!!)
            binding?.rvExerciseStatus?.adapter = exerciseAdapter
        }

        private fun setRestProgressBar(){
            binding?.progressBar?.progress = restProgress
            restTimer = object: CountDownTimer(10000, 1000){
                override fun onTick(millisUntilFinished: Long) {
                    restProgress++
                    binding?.progressBar?.progress = 10 - restProgress
                    binding?.tvTimer?.text = (10 - restProgress).toString()
                }

                override fun onFinish() {
                    currentExercisePosition++
                    exerciseList!![currentExercisePosition].setIsSelected(true)
                    exerciseAdapter!!.notifyDataSetChanged()
                    setUpExerciseView()
                }

            }.start()
        }

        private fun setUpRestView(){
            try{
                val soundURI = Uri.parse("android.resource://com.example.workoutapp/" + R.raw.press_start)
                player = MediaPlayer.create(applicationContext, soundURI)
                player?.isLooping = false
                player?.start()
            }catch (e: Exception){
                e.printStackTrace()
            }
            binding?.flRestView?.visibility = View.VISIBLE
            binding?.title?.visibility = View.VISIBLE
            binding?.tvExercise?.visibility = View.INVISIBLE
            binding?.flExerciseView?.visibility = View.INVISIBLE
            binding?.tvUpcomingLabel?.visibility = View.VISIBLE
            binding?.tvUpcomingExerciseName?.visibility = View.VISIBLE
            if(restTimer != null){
                restTimer!!.cancel()
                restProgress = 0
            }
            speakOut("Rest for 10 seconds. Get ready for" + exerciseList!![currentExercisePosition+1].getName())
            binding?.tvUpcomingExerciseName?.text =
                exerciseList!![currentExercisePosition+1].getName()
            setRestProgressBar()
        }

        private fun setUpExerciseView(){
            binding?.flRestView?.visibility = View.INVISIBLE
            binding?.title?.visibility = View.INVISIBLE
            binding?.tvExercise?.visibility = View.VISIBLE
            binding?.flExerciseView?.visibility = View.VISIBLE
            binding?.ivImage?.visibility = View.VISIBLE
            binding?.tvUpcomingLabel?.visibility = View.INVISIBLE
            binding?.tvUpcomingExerciseName?.visibility = View.INVISIBLE
            if(exerciseTimer != null){
                exerciseTimer?.cancel()
                exerciseProgress = 0
            }
            speakOut("Start exercise" + exerciseList!![currentExercisePosition].getName())
            binding?.ivImage?.setImageResource(exerciseList!![currentExercisePosition].getImage())
            binding?.tvExercise?.text = exerciseList!![currentExercisePosition].getName()

            setExerciseProgressBar()
        }

        override fun onInit(status: Int) {
            if(status == TextToSpeech.SUCCESS){
                val result = tts?.setLanguage(Locale.US)
                if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                    Log.e("TTS", "The language is not supported")
                }
            }
            else{
                Log.e("TTS", "Initialization failed")
            }
        }

        private fun speakOut(text: String){
            tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
        }
        override fun onDestroy(){
        super.onDestroy()
        if(restTimer != null){
            restTimer?.cancel()
            restProgress = 0
        }
        if(exerciseTimer != null){
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }
        if(tts != null){
            tts!!.stop()
            tts!!.shutdown()
        }
        if(player != null){
            player!!.stop()
        }
            binding = null

        }

    override fun onBackPressed() {
        backButton()
    }
    private fun backButton(){
        val customDialog = Dialog(this)
        val dialogBinding = DialogCustomBackConformationBinding.inflate(layoutInflater)
        customDialog.setContentView(dialogBinding.root)
        customDialog.setCanceledOnTouchOutside(false)
        dialogBinding.btnCancel.setOnClickListener{
            customDialog.dismiss()
        }
        dialogBinding.btnExit.setOnClickListener{
            this@ExerciseActivity.finish()
            customDialog.dismiss()
        }
        customDialog.show()
    }

}