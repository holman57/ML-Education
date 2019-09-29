package com.hiteshsahu.stt_tts.demo

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import java.util.*
import android.view.animation.AlphaAnimation
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.widget.TextView
import android.view.View
import android.widget.Button
import com.hiteshsahu.stt_tts.translation_engine.ConversionCallback
import com.hiteshsahu.stt_tts.translation_engine.TranslatorFactory
import android.os.CountDownTimer
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import kotlinx.android.synthetic.main.activity_home.*
import android.support.v4.app.SupportActivity
import android.support.v4.app.SupportActivity.ExtraData
import android.support.v4.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T




class HomeActivity : BasePermissionActivity() {

    private var fadeIn = AlphaAnimation(0.0f, 1.0f)
    private var fadeOut = AlphaAnimation(1.0f, 0.0f)

    override fun getActivityLayout(): Int {
        return com.hiteshsahu.stt_tts.R.layout.activity_home
    }

    override fun setUpView() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(com.hiteshsahu.stt_tts.R.layout.activity_home)

        val constraintLayout = findViewById<ConstraintLayout>(com.hiteshsahu.stt_tts.R.id.constraintLayout)
        val animationDrawable = constraintLayout.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(5000)
        animationDrawable.setExitFadeDuration(2000)

        animationDrawable.start()

        val initText = findViewById<TextView>(com.hiteshsahu.stt_tts.R.id.InitText)
        val displayText = findViewById<TextView>(com.hiteshsahu.stt_tts.R.id.DisplayText)
        val emphasisText = findViewById<TextView>(com.hiteshsahu.stt_tts.R.id.EmphasisText)
        val speechToText = findViewById<FloatingActionButton>(com.hiteshsahu.stt_tts.R.id.speechToText)

        fadeIn.duration = 1200
        fadeIn.fillAfter = true
        fadeOut.startOffset = 1000

        fadeOut.duration = 1200
        fadeOut.fillAfter = true
        fadeOut.startOffset = 10

        initText.startAnimation(fadeIn)

        say("Hello")

        val helloButton = findViewById<Button>(com.hiteshsahu.stt_tts.R.id.HelloButton)
        val leftButton = findViewById<Button>(com.hiteshsahu.stt_tts.R.id.LeftButton)
        val centerButton = findViewById<Button>(com.hiteshsahu.stt_tts.R.id.CenterButton)
        val rightButton = findViewById<Button>(com.hiteshsahu.stt_tts.R.id.RightButton)

        helloButton.visibility = View.VISIBLE
        helloButton.setOnClickListener {
            initText.startAnimation(fadeOut)
            helloButton.startAnimation(fadeOut)

        //    three_phase_failure(displayText, emphasisText, initText, helloButton, speechToText)
            displayText.visibility = View.VISIBLE
            displayText.startAnimation(fadeIn)
            val random = Random().nextInt(26) + 97

            displayText.text = "This is the letter " + random.toChar() + "."
            say("This is the letter. " + random.toChar().toUpperCase() + ".")

            emphasisText.startAnimation(fadeIn)
            emphasisText.textSize = 90.0F
            emphasisText.visibility = View.VISIBLE
            emphasisText.text = random.toChar().toUpperCase() + " " + random.toChar()

        }
    }





    private fun generateLetter(displayText: TextView) {
        displayText.visibility = View.VISIBLE
        displayText.startAnimation(fadeIn)
        displayText.text = "This is the letter "
        say("This is the letter. ")
    }

    private fun three_phase_failure(displayText: TextView, emphasisText: TextView, initText: TextView, helloButton: Button, speechToText: FloatingActionButton) {
        displayText.visibility = View.VISIBLE
        displayText.startAnimation(fadeIn)
        displayText.text = "This is the letter a"
        say("This is the letter. A")

        emphasisText.startAnimation(fadeIn)
        emphasisText.textSize = 90.0F
        emphasisText.visibility = View.VISIBLE
        emphasisText.text = "A a"

        initText.visibility = View.GONE
        helloButton.visibility = View.GONE

        object : CountDownTimer(4000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}
            @SuppressLint("RestrictedApi")
            override fun onFinish() {
                initText.visibility = View.GONE
                emphasisText.startAnimation(fadeIn)
                displayText.startAnimation(fadeIn)
                emphasisText.visibility = View.VISIBLE
                emphasisText.text = "A a"
                display(displayText, "What letter is this?")
                speechToText.visibility = View.VISIBLE

                speechToText.setOnClickListener { view ->
                    TranslatorFactory.instance.with(TranslatorFactory.TRANSLATORS.SPEECH_TO_TEXT,
                            object : ConversionCallback {
                                override fun onSuccess(result: String) {
                                    emphasisText.visibility = View.GONE
                                    speechToText.visibility = View.GONE
                                    emphasisText.text = ""
                                    displayText.startAnimation(fadeIn)
                                    display(displayText, "Good Job!")
                                    Snackbar.make(view, result, Snackbar.LENGTH_LONG).setAction("Action", null).show()
                                }

                                override fun onCompletion() {}
                                override fun onErrorOccurred(errorMessage: String) {
                                    emphasisText.visibility = View.GONE
                                    speechToText.visibility = View.GONE
                                    emphasisText.text = ""
                                    displayText.startAnimation(fadeIn)
                                    display(displayText, "Try Again.")
                                    object : CountDownTimer(2000, 1000) {
                                        override fun onTick(millisUntilFinished: Long) {}
                                        override fun onFinish() {
                                            emphasisText.visibility = View.VISIBLE
                                            speechToText.visibility = View.VISIBLE
                                            speechToText.startAnimation(fadeIn)
                                            displayText.startAnimation(fadeIn)
                                            emphasisText.startAnimation(fadeIn)
                                            emphasisText.text = "A a"
                                            display(displayText, "What letter is this?")
                                        }
                                    }.start()
                                }
                            }).initialize("", this@HomeActivity)
                }
            }
        }.start()
    }



        private fun display(textView: TextView, stringToSpeak: String) {
            textView.text = stringToSpeak
            say(stringToSpeak)
        }

        private fun say(stringToSpeak: String) {
            if (stringToSpeak.isNotEmpty()) {
                TranslatorFactory
                        .instance
                        .with(TranslatorFactory.TRANSLATORS.TEXT_TO_SPEECH,
                                object : ConversionCallback {
                                    override fun onSuccess(result: String) {}
                                    override fun onCompletion() {}
                                    override fun onErrorOccurred(errorMessage: String) {
                                        //   erroConsole.text = "Text2Speech Error: $errorMessage"
                                    }
                                })
                        .initialize(stringToSpeak, this)
            }
        }
}


