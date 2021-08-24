package com.evgenynaz.sportnote.bmi.exercise

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.evgenynaz.sportnote.R
import kotlinx.android.synthetic.main.activity_exercise.*
import kotlinx.android.synthetic.main.dialog_custom_back_confirmation.*
import java.util.*

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var restTimer : CountDownTimer? = null
    private var restProgress = 0
    private var restTime : Long = 10

    private var exerciseTimer : CountDownTimer? = null
    private var exerciseProgress = 0
    private var exerciseTime : Long = 30

    private var exerciseList : ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1

    private var tts : TextToSpeech? = null
    private var player : MediaPlayer? = null

    private var exerciseAdapter : ExerciseStatusAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)

        setSupportActionBar(toolbar_exercise_activity)
        val actionBar = supportActionBar
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)

        }

        toolbar_exercise_activity.setNavigationOnClickListener{
            customDialogForBackButton()
        }

        tts = TextToSpeech(this,this)

        exerciseList = Exercise.defaultExerciseList()
        setupRestView()

        setupExerciseStatusRecyclerView()

    }

    // rest
    private fun setRestProgressBar(){
        progressBar.progress = restProgress
        restTimer = object : CountDownTimer(restTime*1000, 1000){

            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                progressBar.progress = restTime.toInt() - restProgress
                timer_tv.text = (restTime - restProgress).toString()
            }

            override fun onFinish() {
                currentExercisePosition++
                exerciseList!![currentExercisePosition].setIsSelected(true)
                exerciseAdapter!!.notifyDataSetChanged() //update the adapter about change the data
                setupExerciseView()
            }
        }.start()
    }

    private fun setupRestView(){


        rest_view_ll.visibility = View.VISIBLE
        exercise_view_ll.visibility = View.GONE

        if(restTimer != null){
            restTimer!!.cancel()
            restProgress = 0
        }

        next_exercise_tv.text = exerciseList!![currentExercisePosition + 1].getName()
        setRestProgressBar()
    }

    // exercise
    private fun setExerciseProgressBar(){
        exerciseTimer = object : CountDownTimer(exerciseTime*1000, 1000){

            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                exercise_timer_tv.text = (exerciseTime - exerciseProgress).toString()
            }

            override fun onFinish() {
                if(currentExercisePosition < exerciseList?.size!! - 1){
                    exerciseList!![currentExercisePosition].setIsSelected(false)
                    exerciseList!![currentExercisePosition].setIsCompleted(true)
                    exerciseAdapter!!.notifyDataSetChanged() //update the adapter about change the data
                    setupRestView()
                }else{
                    finish()
                    //val intent = Intent(this@ExerciseActivity, FinishActivity::class.java)
                    startActivity(intent)
                }
            }
        }.start()
    }

    private fun setupExerciseView(){

        exercise_view_ll.visibility = View.VISIBLE
        rest_view_ll.visibility = View.GONE

        if(exerciseTimer != null){
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }
        setExerciseProgressBar()

        speakOut(exerciseList!![currentExercisePosition].getName())

        // setting exercise values
        image_iv.setImageResource(exerciseList!![currentExercisePosition].getImage())
        exercise_name_tv.text = exerciseList!![currentExercisePosition].getName()
    }

    override fun onInit(status: Int) {
        if(status == TextToSpeech.SUCCESS){ // check if there is a tts on this mobile

        //  val result = tts!!.setLanguage(Locale.US)
            val result = tts!!.setLanguage(Locale.getDefault())
            // check if the language is installed
            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("TTS", "Язык не установлен на телефоне")
            }
        }else{ //error or stopped
            Log.e("TTS", "Ошибка инициализации")
        }
    }

    private fun speakOut(text: String){
        tts!!.speak(text,TextToSpeech.QUEUE_FLUSH, null, "")
    }

    private fun setupExerciseStatusRecyclerView(){
        //set the recycler view in horizontal way
        exercise_status_rv.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL, false)

        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!,this)
        exercise_status_rv.adapter = exerciseAdapter
    }

    override fun onDestroy() {
        // rest view
        if(restTimer != null){
            restTimer!!.cancel()
            restProgress = 0
        }
        // exercise view
        if(exerciseTimer != null){
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }
        // text to speech
        if(tts!=null){
            tts!!.stop()
            tts!!.shutdown()
        }
        // media player
        if(player!=null){
            player!!.stop()
        }

        super.onDestroy()
    }

    override fun onBackPressed() {
        customDialogForBackButton()
    }

    private fun customDialogForBackButton(){
        val customDialog = Dialog(this)
        customDialog.setContentView(R.layout.dialog_custom_back_confirmation)
        // YES
        customDialog.yes_btn.setOnClickListener {
            finish() // go back to main activity
            customDialog.dismiss() // close the dialog
        }
        // NO
        customDialog.no_btn.setOnClickListener {
            customDialog.dismiss() // close the dialog
        }

        customDialog.show()
    }

}
